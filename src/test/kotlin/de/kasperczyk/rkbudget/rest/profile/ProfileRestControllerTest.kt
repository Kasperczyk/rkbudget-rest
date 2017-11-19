package de.kasperczyk.rkbudget.rest.profile

import de.kasperczyk.rkbudget.rest.AbstractRestControllerTest
import de.kasperczyk.rkbudget.rest.exception.DuplicateEmailAddressException
import de.kasperczyk.rkbudget.rest.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.testEmailAddress
import de.kasperczyk.rkbudget.rest.testProfile
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ProfileRestController::class)
@Suppress("UNCHECKED_CAST")
class ProfileRestControllerTest : AbstractRestControllerTest() {

    override val REQUEST_URL = "/profiles"

    @MockBean
    private lateinit var profileServiceMock: ProfileService

    private lateinit var jsonProfile: String

    @Before
    fun setup() {
        jsonProfile = objectMapper.writeValueAsString(testProfile)
    }

    @Test
    fun `POST for a new profile returns status code 201 (created) and creates and returns the profile`() {
        `when`(profileServiceMock.createProfile(testProfile)).thenReturn(testProfile)
        val result = performRequestForObject(doPostRequest(content = jsonProfile), status().isCreated, Profile::class.java)
        assertThat(result, `is`(testProfile))
        verify(profileServiceMock).createProfile(testProfile)
    }

    @Test
    fun `POST with an already existing email address returns status code 409 (conflict) and a ServerError object`() {
        `when`(profileServiceMock.createProfile(testProfile)).thenThrow(DuplicateEmailAddressException(testProfile))
        val result = performRequestForServerError(doPostRequest(content = jsonProfile), status().isConflict)
        assertServerError(result,
                expectedErrorMessage = "A profile with email address '${testProfile.emailAddress.fullAddress}' has already been registered",
                expectedRequestBody = jsonProfile)
        verify(profileServiceMock).createProfile(testProfile)
    }

    @Test
    fun `POST without a valid body returns status code 400 (bad request) and a ServerError object`() {
        val result = performRequestForServerError(doPostRequest(content = "invalid"), status().isBadRequest)
        assertServerError(result, expectedErrorMessage = "Required request body of type ${Profile::class} is missing")
        verify(profileServiceMock, never()).createProfile(testProfile)
    }

    @Test
    fun `GET for an available profile returns status code 200 (ok) and the profile`() {
        `when`(profileServiceMock.getProfileByEmailAddress(testEmailAddress)).thenReturn(testProfile)
        val result = performRequestForObject(doGetRequest("/${testEmailAddress.fullAddress}"), status().isOk, Profile::class.java)
        assertThat(result, `is`(testProfile))
        verify(profileServiceMock).getProfileByEmailAddress(testEmailAddress)
    }

    @Test
    fun `GET for a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(profileServiceMock.getProfileByEmailAddress(testEmailAddress))
                .thenThrow(ProfileNotFoundException(testEmailAddress))
        val result = performRequestForServerError(doGetRequest("/${testEmailAddress.fullAddress}"), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Profile with email address '${testEmailAddress.fullAddress}' not found",
                expectedPathParameters = mapOf("profileEmailAddress" to testEmailAddress.fullAddress))
        verify(profileServiceMock).getProfileByEmailAddress(testEmailAddress)
    }

    @Test
    fun `PUT on an existing profile updates it and returns status code 204 (no content)`() {
        performRequest(doPutRequest(REQUEST_URL + "/${testProfile.id}", content = jsonProfile), status().isNoContent)
        verify(profileServiceMock).updateProfile(testProfile)
    }

    @Test
    fun `PUT on a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(profileServiceMock.updateProfile(testProfile))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val result = performRequestForServerError(doPutRequest(
                requestUrl = REQUEST_URL + "/${testProfile.id}", content = jsonProfile), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id}' not found",
                expectedPathParameters = mapOf("profileId" to testProfile.id.toString()))
        verify(profileServiceMock).updateProfile(testProfile)
    }

    @Test
    fun `PUT without a valid body returns status code 400 (bad request) and a ServerError object`() {
        val result = performRequestForServerError(doPutRequest(
                requestUrl = REQUEST_URL + "/${testProfile.id}", content = "invalid"),
                status().isBadRequest)
        assertServerError(result, expectedErrorMessage = "Required request body of type ${Profile::class} is missing")
        verify(profileServiceMock, never()).createProfile(testProfile)
    }

    @Test
    fun `PUT returns status code 400 (bad request) and a ServerError object if the ids do not match`() {
        val result = performRequestForServerError(doPutRequest(
                requestUrl = REQUEST_URL + "/${testProfile.id - 1}", content = jsonProfile),
                status().isBadRequest)
        assertServerError(result,
                expectedErrorMessage = "Ids do not match. The id specified is '${testProfile.id - 1}', " +
                        "the object in the request body contained id '${testProfile.id}'",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id - 1}"))
        verify(profileServiceMock, never()).updateProfile(testProfile)
    }

    @Test
    fun `DELETE on an existing profile deletes it and returns status code 200 (ok)`() {
        performRequest(doDeleteRequest(requestUrl = REQUEST_URL + "/${testProfile.id}"), status().isOk)
        verify(profileServiceMock).deleteProfile(testProfile.id)
    }

    @Test
    fun `DELETE on a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(profileServiceMock.deleteProfile(testProfile.id))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val result = performRequestForServerError(doDeleteRequest(
                requestUrl = REQUEST_URL + "/${testProfile.id}"), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id}' not found",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id}"))
        verify(profileServiceMock).deleteProfile(testProfile.id)
    }
}