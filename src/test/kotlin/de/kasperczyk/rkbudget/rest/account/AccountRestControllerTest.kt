package de.kasperczyk.rkbudget.rest.account

import com.fasterxml.jackson.databind.ObjectMapper
import de.kasperczyk.rkbudget.rest.ServerError
import de.kasperczyk.rkbudget.rest.account.entity.Account
import de.kasperczyk.rkbudget.rest.profile.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.testAccount
import de.kasperczyk.rkbudget.rest.testProfile
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@WebMvcTest(AccountRestController::class, secure = false)
class AccountRestControllerTest {

    private val REQUEST_URL = "/profiles/${testProfile.id}/accounts"

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

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
        val jsonResult = mockMvc
                .perform(post(REQUEST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAccount))
                .andExpect(status().isCreated)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, Account::class.java)
        verify(accountServiceMock).createAccount(profileId = testProfile.id, account = testAccount)
        assertThat(result, `is`(testAccount))
    }

    @Test
    fun `POST for a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(accountServiceMock.createAccount(testProfile.id, testAccount))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val jsonResult = mockMvc.perform(post(REQUEST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonAccount))
                .andExpect(status().isNotFound)
                .andReturn().response.contentAsString
        val result = objectMapper.readValue(jsonResult, ServerError::class.java)
        verify(accountServiceMock).createAccount(profileId = testProfile.id, account = testAccount)
        assertThat(result.errorMessage, `is`("Profile with id '${testProfile.id}' not found"))
        assertThat(result.pathParameters!!["profileId"], `is`("${testProfile.id}"))
    }

    @Test
    fun `GET for all accounts of an existing profile returns all accounts and status code 200 (ok)`() {
        mockMvc.perform(get(REQUEST_URL))
                .andExpect(status().isOk)
    }
}