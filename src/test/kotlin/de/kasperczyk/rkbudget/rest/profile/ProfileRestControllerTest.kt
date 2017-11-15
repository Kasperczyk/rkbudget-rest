package de.kasperczyk.rkbudget.rest.profile

import com.fasterxml.jackson.databind.ObjectMapper
import de.kasperczyk.rkbudget.rest.ServerError
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.profile.exception.DuplicateEmailAddressException
import de.kasperczyk.rkbudget.rest.profile.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.testEmailAddress
import de.kasperczyk.rkbudget.rest.testProfile
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.isEmptyString
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(ProfileRestController::class, secure = false)
class ProfileRestControllerTest {

    private val REQUEST_URL = "/profiles"

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

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
        val jsonResult = mockMvc
                .perform(post(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProfile))
                .andExpect(status().isCreated)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, Profile::class.java)
        assertThat(result, `is`(testProfile))
        verify(profileServiceMock).createProfile(testProfile)
    }

    @Test
    fun `POST with an existing email address returns status code 409 (conflict) and a ServerError object`() {
        `when`(profileServiceMock.createProfile(testProfile)).thenThrow(DuplicateEmailAddressException(testEmailAddress))
        val jsonResult = mockMvc
                .perform(post(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProfile))
                .andExpect(status().isConflict)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, ServerError::class.java)
        assertThat(result.errorMessage, `is`("A profile with email address " +
                "'${testProfile.emailAddress.fullAddress}' has already been registered"))
        assertThat(result.requestParameters!!["emailAddress"], `is`(testProfile.emailAddress.fullAddress))
        assertThat(result.pathParameters, `is`(nullValue()))
        verify(profileServiceMock).createProfile(testProfile)
    }

    @Test
    fun `POST without a valid body returns status code 400 (bad request) and a ServerError object`() {
        val jsonResult = mockMvc
                .perform(post(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, ServerError::class.java)
        assertThat(result.errorMessage, `is`("Required request body of type ${Profile::class} is missing"))
        assertThat(result.pathParameters, `is`(nullValue()))
        assertThat(result.requestParameters, `is`(nullValue()))
        verify(profileServiceMock, never()).createProfile(testProfile)
    }

    @Test
    fun `GET for an available profile returns status code 200 (ok) and the profile`() {
        `when`(profileServiceMock.getProfileByEmailAddress(testEmailAddress)).thenReturn(testProfile)
        val jsonResult = mockMvc
                .perform(get("$REQUEST_URL/${testEmailAddress.fullAddress}"))
                .andExpect(status().isOk)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, Profile::class.java)
        assertThat(result, `is`(testProfile))
        verify(profileServiceMock).getProfileByEmailAddress(testEmailAddress)
    }

    @Test
    fun `GET for a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(profileServiceMock.getProfileByEmailAddress(testEmailAddress))
                .thenThrow(ProfileNotFoundException(testEmailAddress))
        val jsonResult = mockMvc
                .perform(get("$REQUEST_URL/${testEmailAddress.fullAddress}"))
                .andExpect(status().isNotFound)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, ServerError::class.java)
        assertThat(result.errorMessage, `is`(not(isEmptyString())))
        assertThat(result.pathParameters?.get("profileEmailAddress"), `is`(testEmailAddress.fullAddress))
        assertThat(result.requestParameters, `is`(nullValue()))
        verify(profileServiceMock).getProfileByEmailAddress(testEmailAddress)
    }

    @Test
    fun `PUT on an existing profile updates it and returns status code 204 (no content)`() {
        mockMvc
                .perform(put("$REQUEST_URL/${testProfile.id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProfile))
                .andExpect(status().isNoContent)
        verify(profileServiceMock).updateProfile(testProfile)
    }

    @Test
    fun `PUT on a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(profileServiceMock.updateProfile(testProfile))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val jsonResult = mockMvc
                .perform(put("$REQUEST_URL/${testProfile.id}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProfile))
                .andExpect(status().isNotFound)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, ServerError::class.java)
        assertThat(result.errorMessage, `is`("Profile with id '${testProfile.id}' not found"))
        assertThat(result.pathParameters?.get("profileId"), `is`(testProfile.id.toString()))
        assertThat(result.requestParameters, `is`(nullValue()))
        verify(profileServiceMock).updateProfile(testProfile)
    }

    @Test
    fun `PUT returns status code 400 (bad request) and a ServerError object if the ids do not match`() {
        val jsonResult = mockMvc
                .perform(put("$REQUEST_URL/${testProfile.id - 1}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProfile))
                .andExpect(status().isBadRequest)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, ServerError::class.java)
        assertThat(result.errorMessage, `is`("Ids do not match. The path specified id '${testProfile.id - 1}', " +
                "the object in the request body contained id '${testProfile.id}'"))
        assertThat(result.pathParameters?.get("profileId"), `is`("${testProfile.id - 1}"))
        assertThat(result.requestParameters?.get("profile"), `is`(testProfile.toString()))
        verify(profileServiceMock, never()).updateProfile(testProfile)
    }

    @Test
    fun `DELETE on an existing profile deletes it and returns status code 200 (ok)`() {
        mockMvc
                .perform(delete("$REQUEST_URL/${testProfile.id}"))
                .andExpect(status().isOk)
        verify(profileServiceMock).deleteProfile(testProfile.id)
    }

    @Test
    fun `DELETE on a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(profileServiceMock.deleteProfile(testProfile.id))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val jsonResult = mockMvc
                .perform(delete("$REQUEST_URL/${testProfile.id}"))
                .andExpect(status().isNotFound)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, ServerError::class.java)
        assertThat(result.errorMessage, `is`("Profile with id '${testProfile.id}' not found"))
        assertThat(result.pathParameters?.get("profileId"), `is`(testProfile.id.toString()))
        assertThat(result.requestParameters, `is`(nullValue()))
        verify(profileServiceMock).deleteProfile(testProfile.id)
    }
}