package de.kasperczyk.rkbudget.rest.sharer

import de.kasperczyk.rkbudget.rest.AbstractRestControllerTest
import de.kasperczyk.rkbudget.rest.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.sharer.entity.Sharer
import de.kasperczyk.rkbudget.rest.testProfile
import de.kasperczyk.rkbudget.rest.testSharer
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

@WebMvcTest(SharerRestController::class)
class SharerRestControllerTest : AbstractRestControllerTest() {

    override val REQUEST_URL = "/profiles/${testProfile.id}/sharers"

    @MockBean
    private lateinit var sharerServiceMock: SharerService

    private lateinit var jsonSharer: String

    @Before
    fun setup() {
        jsonSharer = objectMapper.writeValueAsString(testSharer)
    }

    @Test
    fun `POST for a new sharer creates it and returns the new sharer and status code 201 (created)`() {
        `when`(sharerServiceMock.createSharer(testProfile.id, testSharer)).thenReturn(testSharer)
        val result = performRequestForObject(doPostRequest(content = jsonSharer), status().isCreated, Sharer::class.java)
        verify(sharerServiceMock).createSharer(testProfile.id, testSharer)
        assertThat(result, `is`(testSharer))
    }

    @Test
    fun `POST for a non-existen profile returns status code 404 (not found) and a ServerError object`() {
        `when`(sharerServiceMock.createSharer(testProfile.id, testSharer))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val result = performRequestForServerError(doPostRequest(content = jsonSharer), status().isNotFound)
        verify(sharerServiceMock).createSharer(testProfile.id, testSharer)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id}' not found",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id}"))
        verify(sharerServiceMock).createSharer(testProfile.id, testSharer)
    }

    @Test
    fun `POST without a valid body returns status code 400 (bad request) and a ServerError object`() {
        val result = performRequestForServerError(doPostRequest(content = "invalid"), status().isBadRequest)
        assertServerError(result, expectedErrorMessage = "Required request body of type ${Sharer::class} is missing")
    }

    @Test
    fun `GET for an available profile returns status code 200 (ok) and all sharers of that profile`() {
        val accountList = listOf(testSharer, testSharer)
        `when`(sharerServiceMock.getAllSharersForProfile(testProfile.id)).thenReturn(accountList)
        val result: List<Sharer> = listOf(*performRequestForObject(doGetRequest(), status().isOk, Array<Sharer>::class.java))
        assertEquals(result, accountList)
        verify(sharerServiceMock).getAllSharersForProfile(testProfile.id)
    }

    @Test
    fun `GET for a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(sharerServiceMock.getAllSharersForProfile(testProfile.id))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val result = performRequestForServerError(doGetRequest(), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id}' not found",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id}"))
        verify(sharerServiceMock).getAllSharersForProfile(testProfile.id)
    }
}