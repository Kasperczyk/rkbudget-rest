package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.AbstractRestControllerTest
import de.kasperczyk.rkbudget.rest.account.entity.Account
import de.kasperczyk.rkbudget.rest.account.entity.GiroAccount
import de.kasperczyk.rkbudget.rest.account.exception.AccountNotFoundException
import de.kasperczyk.rkbudget.rest.profile.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.testAccount
import de.kasperczyk.rkbudget.rest.testProfile
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(AccountRestController::class)
class AccountRestControllerTest : AbstractRestControllerTest() {

    override val REQUEST_URL = "/profiles/${testProfile.id}/accounts"

    @MockBean
    private lateinit var accountServiceMock: AccountService

    private lateinit var jsonAccount: String

    @Before
    fun setup() {
        jsonAccount = objectMapper.writeValueAsString(testAccount)
    }

    @Test
    fun `POST for a new account creates it and returns the new account and status code 201 (created)`() {
        `when`(accountServiceMock.createAccount(profileId = testProfile.id, account = testAccount)).thenReturn(testAccount)
        val result = performRequestForObject(doPostRequest(content = jsonAccount), status().isCreated, Account::class.java)
        verify(accountServiceMock).createAccount(profileId = testProfile.id, account = testAccount)
        assertThat(result as GiroAccount, `is`(testAccount))
    }

    @Test
    fun `POST for a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(accountServiceMock.createAccount(testProfile.id, testAccount))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val result = performRequestForServerError(doPostRequest(content = jsonAccount), status().isNotFound)
        verify(accountServiceMock).createAccount(profileId = testProfile.id, account = testAccount)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id}' not found",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id}"))
        verify(accountServiceMock).createAccount(testProfile.id, testAccount)
    }

    @Test
    fun `POST without a valid body returns status code 400 (bad request) and a ServerError object`() {
        val result = performRequestForServerError(doPostRequest(content = "invalid"), status().isBadRequest)
        assertServerError(result, expectedErrorMessage = "Required request body of type ${Account::class} is missing")
    }

    @Test
    fun `GET for a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(accountServiceMock.getAllAccountsForProfile(testProfile.id))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val result = performRequestForServerError(doGetRequest(), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id}' not found",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id}"))
        verify(accountServiceMock).getAllAccountsForProfile(testProfile.id)
    }

    @Test
    fun `GET for an available profile returns status code 200 (ok) and all accounts of that profile`() {
        val accountList = listOf(testAccount, testAccount)
        `when`(accountServiceMock.getAllAccountsForProfile(testProfile.id)).thenReturn(accountList)
        val result: List<Account> = listOf(*performRequestForObject(doGetRequest(), status().isOk, Array<Account>::class.java))
        assertEquals(result, accountList)
        verify(accountServiceMock).getAllAccountsForProfile(testProfile.id)
    }

    @Test
    fun `PUT on a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(accountServiceMock.updateAccount(testAccount.id, testAccount))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val result = performRequestForServerError(doPutRequest(
                requestUrl = REQUEST_URL + "/${testAccount.id}", content = jsonAccount), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id}' not found",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id}"))
        verify(accountServiceMock).updateAccount(testAccount.id, testAccount)
    }

    @Test
    fun `PUT on a non-existent account returns status code 404 (not found) and a ServerError object`() {
        `when`(accountServiceMock.updateAccount(testAccount.id, testAccount))
                .thenThrow(AccountNotFoundException(testAccount.id))
        val result = performRequestForServerError(doPutRequest(
                requestUrl = REQUEST_URL + "/${testAccount.id}", content = jsonAccount), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Account with id '${testAccount.id}' not found",
                expectedPathParameters = mapOf("accountId" to "${testAccount.id}"))
        verify(accountServiceMock).updateAccount(testAccount.id, testAccount)
    }

    @Test
    fun `PUT returns status code 400 (bad request) and a ServerError object if the profile ids do not match`() {
        val result = performRequestForServerError(doPutRequest(
                requestUrl = "/profiles/${testProfile.id - 1}/accounts/${testAccount.id}", content = jsonAccount), status().isBadRequest)
        assertServerError(result,
                expectedErrorMessage = "Ids do not match. The id specified is '${testProfile.id - 1}', " +
                        "the object in the request body contained id '${testAccount.id}'",
                expectedPathParameters = mapOf("profileId" to "${testAccount.id - 1}"))
    }

    @Test
    fun `PUT returns status code 400 (bad request) and a ServerError object if the account ids do not match`() {
        val result = performRequestForServerError(doPutRequest(
                requestUrl = REQUEST_URL + "/${testAccount.id - 1}", content = jsonAccount), status().isBadRequest)
        assertServerError(result,
                expectedErrorMessage = "Ids do not match. The id specified is '${testAccount.id - 1}', " +
                        "the object in the request body contained id '${testAccount.id}'",
                expectedPathParameters = mapOf("accountId" to "${testAccount.id - 1}"))
    }

    @Test
    fun `PUT on an existing account updates it and returns status code 204 (no content)`() {
        performRequest(doPutRequest(
                requestUrl = REQUEST_URL + "/${testAccount.id}", content = jsonAccount), status().isNoContent)
        verify(accountServiceMock).updateAccount(testAccount.id, testAccount)
    }

    @Test
    fun `DELETE on a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(accountServiceMock.deleteAccount(testProfile.id - 1, testAccount.id))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id - 1))
        val result = performRequestForServerError(doDeleteRequest(
                requestUrl = "/profiles/${testProfile.id - 1}/accounts/${testAccount.id}"), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id - 1}' not found",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id - 1}"))
        verify(accountServiceMock).deleteAccount(testProfile.id - 1, testAccount.id)
    }

    @Test
    fun `DELETE on a non-existent account returns status code 404 (not found) and a ServerError object`() {
        `when`(accountServiceMock.deleteAccount(testProfile.id, testAccount.id - 1))
                .thenThrow(AccountNotFoundException(testAccount.id - 1))
        val result = performRequestForServerError(doDeleteRequest(
                requestUrl = REQUEST_URL + "/${testAccount.id - 1}"), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Account with id '${testAccount.id - 1}' not found",
                expectedPathParameters = mapOf("accountId" to "${testAccount.id - 1}"))
        verify(accountServiceMock).deleteAccount(testProfile.id, testAccount.id - 1)
    }

    @Test
    fun `DELETE on an existing account deletes it and returns status code 200 (ok)`() {
        performRequest(doDeleteRequest(requestUrl = REQUEST_URL + "/${testAccount.id}"), status().isOk)
        verify(accountServiceMock).deleteAccount(testProfile.id, testAccount.id)
    }
}