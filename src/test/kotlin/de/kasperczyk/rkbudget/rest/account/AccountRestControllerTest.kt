package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.AbstractRestControllerTest
import de.kasperczyk.rkbudget.rest.ServerError
import de.kasperczyk.rkbudget.rest.account.entity.Account
import de.kasperczyk.rkbudget.rest.account.entity.GiroAccount
import de.kasperczyk.rkbudget.rest.profile.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.testAccount
import de.kasperczyk.rkbudget.rest.testProfile
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
        val result = performRequestForObject(doPostRequest(content = jsonAccount), status().isNotFound, ServerError::class.java)
        verify(accountServiceMock).createAccount(profileId = testProfile.id, account = testAccount)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id}' not found",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id}"),
                expectedRequestParameters = null,
                expectedRequestBody = null)
    }

    @Test
    fun `POST without a valid body returns status code 400 (bad request) and a ServerError object`() {

    }

    @Test
    fun `GET for a non-existent profile returns status code 404 (not found) and a ServerError object`() {

    }

    @Test
    fun `GET for a non-existent account returns status code 404 (not found) and a ServerError object`() {

    }

    @Test
    fun `GET for an available profile returns status code 200 (ok) and all accounts of that profile`() {

    }

    @Test
    fun `PUT on a non-existent profile returns status code 404 (not found) and a ServerError object`() {

    }

    @Test
    fun `PUT on a non-existent account returns status code 404 (not found) and a ServerError object`() {

    }

    @Test
    fun `PUT returns status code 400 (bad request) and a ServerError object if the ids do not match`() {

    }

    @Test
    fun `PUT on an existing account updates it and returns status code 204 (no content)`() {

    }

    @Test
    fun `DELETE on a non-existent profile returns status code 404 (not found) and a ServerError object`() {

    }

    @Test
    fun `DELETE on a non-existent account returns status code 404 (not found) and a ServerError object`() {

    }

    @Test
    fun `DELETE on an existing account deletes it and returns status code 200 (ok)`() {

    }
}