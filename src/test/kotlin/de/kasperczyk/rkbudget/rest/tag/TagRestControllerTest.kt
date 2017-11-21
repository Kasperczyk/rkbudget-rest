package de.kasperczyk.rkbudget.rest.tag

import de.kasperczyk.rkbudget.rest.AbstractRestControllerTest
import de.kasperczyk.rkbudget.rest.exception.EntityNotFoundException
import de.kasperczyk.rkbudget.rest.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import de.kasperczyk.rkbudget.rest.testProfile
import de.kasperczyk.rkbudget.rest.testTag
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

@WebMvcTest(TagRestController::class)
class TagRestControllerTest : AbstractRestControllerTest() {

    override val REQUEST_URL: String = "/profiles/${testProfile.id}/tags"

    @MockBean
    private lateinit var tagServiceMock: TagService

    private lateinit var jsonTag: String

    @Before
    fun setUp() {
        jsonTag = objectMapper.writeValueAsString(testTag)
    }

    @Test
    fun `POST for a new tag creates it and returns the new tag and status code 201 (created)`() {
        `when`(tagServiceMock.createTag(profileId = testProfile.id, tag = testTag)).thenReturn(testTag)
        val result = performRequestForObject(doPostRequest(content = jsonTag), status().isCreated, Tag::class.java)
        verify(tagServiceMock).createTag(profileId = testProfile.id, tag = testTag)
        assertThat(result, `is`(testTag))
    }

    @Test
    fun `POST for a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(tagServiceMock.createTag(testProfile.id, testTag))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val result = performRequestForServerError(doPostRequest(content = jsonTag), status().isNotFound)
        verify(tagServiceMock).createTag(profileId = testProfile.id, tag = testTag)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id}' not found",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id}"))
        verify(tagServiceMock).createTag(testProfile.id, testTag)
    }

    @Test
    fun `POST without a valid body returns status code 400 (bad request) and a ServerError object`() {
        val result = performRequestForServerError(doPostRequest(content = "invalid"), status().isBadRequest)
        assertServerError(result, expectedErrorMessage = "Required request body of type ${Tag::class} is missing")
    }

    @Test
    fun `GET for an available profile returns status code 200 (ok)  and all tags of that profile`() {
        val tagList = listOf(testTag, testTag)
        `when`(tagServiceMock.getAllTagsForProfile(testProfile.id)).thenReturn(tagList)
        val result: List<Tag> = listOf(*performRequestForObject(doGetRequest(), status().isOk, Array<Tag>::class.java))
        assertEquals(result, tagList)
        verify(tagServiceMock).getAllTagsForProfile(testProfile.id)
    }

    @Test
    fun `GET for a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(tagServiceMock.getAllTagsForProfile(testProfile.id))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val result = performRequestForServerError(doGetRequest(), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id}' not found",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id}"))
        verify(tagServiceMock).getAllTagsForProfile(testProfile.id)
    }

    @Test
    fun `PUT on an existing tag updates it and returns status code 204 (no content)`() {
        performRequest(doPutRequest(
                requestUrl = REQUEST_URL + "/${testTag.id}", content = jsonTag), status().isNoContent)
        verify(tagServiceMock).updateTag(testTag.id, testTag)
    }

    @Test
    fun `PUT on a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(tagServiceMock.getAllTagsForProfile(testProfile.id))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val result = performRequestForServerError(doGetRequest(), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id}' not found",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id}"))
        verify(tagServiceMock).getAllTagsForProfile(testProfile.id)
    }

    @Test
    fun `PUT on a non-existent tag returns status code 404 (not found) and a ServerError object`() {
        `when`(tagServiceMock.updateTag(testTag.id, testTag))
                .thenThrow(EntityNotFoundException(testTag.id, "tagId"))
        val result = performRequestForServerError(doPutRequest(
                requestUrl = REQUEST_URL + "/${testTag.id}", content = jsonTag), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Tag with id '${testTag.id}' not found",
                expectedPathParameters = mapOf("tagId" to "${testTag.id}"))
        verify(tagServiceMock).updateTag(testTag.id, testTag)
    }

    @Test
    fun `PUT returns status code 400 (bad request) and a ServerError object if the profile ids do not match`() {
        val result = performRequestForServerError(doPutRequest(
                requestUrl = REQUEST_URL + "/${testTag.id - 1}", content = jsonTag), status().isBadRequest)
        assertServerError(result,
                expectedErrorMessage = "Ids do not match. The id specified is '${testTag.id - 1}', " +
                        "the object in the request body contained id '${testTag.id}'",
                expectedPathParameters = mapOf("tagId" to "${testTag.id - 1}"))
    }

    @Test
    fun `DELETE on an existing tag deletes it and returns status code 200 (ok)`() {
        performRequest(doDeleteRequest(requestUrl = REQUEST_URL + "/${testTag.id}"), status().isOk)
        verify(tagServiceMock).deleteTag(testProfile.id, testTag.id)
    }

    @Test
    fun `DELETE on a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(tagServiceMock.deleteTag(testProfile.id - 1, testTag.id))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id - 1))
        val result = performRequestForServerError(doDeleteRequest(
                requestUrl = "/profiles/${testProfile.id - 1}/tags/${testTag.id}"), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id - 1}' not found",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id - 1}"))
        verify(tagServiceMock).deleteTag(testProfile.id - 1, testTag.id)
    }

    @Test
    fun `DELETE on a non-existent tag returns status code 404 (not found) and a ServerError object`() {
        `when`(tagServiceMock.deleteTag(testProfile.id, testTag.id - 1))
                .thenThrow(EntityNotFoundException(testTag.id - 1, "tagId"))
        val result = performRequestForServerError(doDeleteRequest(
                requestUrl = REQUEST_URL + "/${testTag.id - 1}"), status().isNotFound)
        assertServerError(result,
                expectedErrorMessage = "Tag with id '${testTag.id - 1}' not found",
                expectedPathParameters = mapOf("tagId" to "${testTag.id - 1}"))
        verify(tagServiceMock).deleteTag(testProfile.id, testTag.id - 1)
    }
}