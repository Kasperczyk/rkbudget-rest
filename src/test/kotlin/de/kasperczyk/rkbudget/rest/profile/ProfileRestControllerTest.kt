package de.kasperczyk.rkbudget.rest.profile

import com.fasterxml.jackson.databind.ObjectMapper
import de.kasperczyk.rkbudget.rest.RkbudgetRestApplication
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(ProfileRestController::class, secure = false)
@ContextConfiguration(classes = arrayOf(RkbudgetRestApplication::class))
class ProfileRestControllerTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var profileService: ProfileService

    private lateinit var jsonProfile: String

    @Before
    fun setup() {
        jsonProfile = objectMapper.writeValueAsString(testProfile)
    }

    @Test
    fun `POST for a new profile returns status code 201 (created) and creates and returns the profile`() {
        `when`(profileService.createProfile(testProfile)).thenReturn(testProfile)
        val jsonProfile = objectMapper.writeValueAsString(testProfile)
        val jsonResult = mockMvc
                .perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProfile))
                .andExpect(status().isCreated)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, Profile::class.java)
        assertThat(result, `is`(testProfile))
        verify(profileService).createProfile(testProfile)
    }

    @Test
    fun `POST with an existing email address returns status code 409 (conflict) and a ServerError object`() {
        `when`(profileService.createProfile(testProfile)).thenThrow(DuplicateEmailAddressException(testEmailAddress))
        val jsonResult = mockMvc
                .perform(post("/profiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonProfile))
                .andExpect(status().isConflict)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, ServerError::class.java)
        assertThat(result.errorMessage, `is`(not(isEmptyString())))
        assertThat(result.parameters, `is`(nullValue()))
        verify(profileService).createProfile(testProfile)
    }

    @Test
    fun `GET for an available profile returns status code 200 (ok) and the profile`() {
        `when`(profileService.getProfileByEmailAddress(testEmailAddress)).thenReturn(testProfile)
        val jsonResult = mockMvc
                .perform(get("/profiles/${testEmailAddress.fullAddress}"))
                .andExpect(status().isOk)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, Profile::class.java)
        assertThat(result, `is`(testProfile))
        verify(profileService).getProfileByEmailAddress(testEmailAddress)
    }

    @Test
    fun `GET for a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(profileService.getProfileByEmailAddress(testEmailAddress))
                .thenThrow(ProfileNotFoundException(testEmailAddress))
        val jsonResult = mockMvc
                .perform(get("/profiles/${testEmailAddress.fullAddress}"))
                .andExpect(status().isNotFound)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, ServerError::class.java)
        assertThat(result.errorMessage, `is`(not(isEmptyString())))
        assertThat(result.parameters?.get("profileEmailAddress"), `is`(testEmailAddress.fullAddress))
        verify(profileService).getProfileByEmailAddress(testEmailAddress)
    }

    @Test
    fun `PUT on an existing profile updates it and returns status code 204 (no content)`() {
        mockMvc.perform(put("/profiles/${testProfile.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonProfile))
                .andExpect(status().isNoContent)
        verify(profileService).updateProfile(testProfile.id, testProfile)
    }

    @Test
    fun `PUT on a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(profileService.updateProfile(testProfile.id, testProfile))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val jsonResult = mockMvc.perform(put("/profiles/${testProfile.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonProfile))
                .andExpect(status().isNotFound)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, ServerError::class.java)
        assertThat(result.errorMessage, `is`("Profile with id '${testProfile.id}' not found"))
        assertThat(result.parameters?.get("profileId"), `is`(testProfile.id.toString()))
    }

    @Test
    fun `DELETE on an existing profile deletes it and returns status code 200 (ok)`() {
        mockMvc.perform(delete("/profiles/${testProfile.id}"))
                .andExpect(status().isOk)
        verify(profileService).deleteProfile(testProfile.id)
    }

    @Test
    fun `DELETE on a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(profileService.deleteProfile(testProfile.id))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val jsonResult = mockMvc.perform(delete("/profiles/${testProfile.id}"))
                .andExpect(status().isNotFound)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, ServerError::class.java)
        assertThat(result.errorMessage, `is`("Profile with id '${testProfile.id}' not found"))
        assertThat(result.parameters?.get("profileId"), `is`(testProfile.id.toString()))
    }
}