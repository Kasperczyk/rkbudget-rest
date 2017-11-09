package de.kasperczyk.rkbudget.rest.profile

import com.fasterxml.jackson.databind.ObjectMapper
import de.kasperczyk.rkbudget.rest.RkbudgetRestApplication
import de.kasperczyk.rkbudget.rest.ServerError
import de.kasperczyk.rkbudget.rest.config.TestConfig
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.isEmptyString
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner::class)
@WebMvcTest(ProfileRestController::class, secure = false)
@ContextConfiguration(classes = arrayOf(TestConfig::class, RkbudgetRestApplication::class))
class ProfileRestControllerTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var profileService: ProfileService

    @Test
    fun `GET for an available profile returns status code 200 and the profile`() {
        val emailAddress = EmailAddress(fullAddress = "kasperczyk.rene@gmail.com")
        val profile = Profile(emailAddress = emailAddress)
        `when`(profileService.getProfileByEmailAddress(emailAddress)).thenReturn(profile)
        val jsonResult = mockMvc
                .perform(get("/profiles/${emailAddress.fullAddress}"))
                .andExpect(status().isOk)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, Profile::class.java)
        assertThat(result, `is`(profile))
        verify(profileService).getProfileByEmailAddress(emailAddress)
    }

    @Test
    fun `GET for a non-existent profile returns status code 404 and a ServerError object with an error message`() {
        val emailAddress = EmailAddress(fullAddress = "no.email@gmail.com")
        `when`(profileService.getProfileByEmailAddress(emailAddress)).thenThrow(ProfileNotFoundException(emailAddress))
        val jsonResult = mockMvc
                .perform(get("/profiles/${emailAddress.fullAddress}"))
                .andExpect(status().isNotFound)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, ServerError::class.java)
        assertThat(result.errorMessage, `is`(not(isEmptyString())))
        assertThat(result.parameters?.get("profileEmailAddress"), `is`(emailAddress.fullAddress))
        verify(profileService).getProfileByEmailAddress(emailAddress)
    }
}