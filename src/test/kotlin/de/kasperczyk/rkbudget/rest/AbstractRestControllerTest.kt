package de.kasperczyk.rkbudget.rest

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*

// subclasses need to be annotated with @WebMvcTest and pass the RestController class that is going to be tested as a value parameter
@RunWith(SpringRunner::class)
@Suppress("UNCHECKED_CAST")
abstract class AbstractRestControllerTest {

    abstract val REQUEST_URL: String

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    protected fun <T : Any> performRequestForObject(request: MockHttpServletRequestBuilder,
                                                    expectedResult: ResultMatcher,
                                                    valueType: Class<T>): T =
            objectMapper.readValue(
                    mockMvc.perform(request)
                            .andExpect(expectedResult)
                            .andReturn().response.contentAsString,
                    valueType)

    protected fun performRequestForServerError(request: MockHttpServletRequestBuilder,
                                               expectedResult: ResultMatcher) =
            performRequestForObject(request, expectedResult, ServerError::class.java)

    protected fun performRequest(request: MockHttpServletRequestBuilder, expectedResult: ResultMatcher) =
            mockMvc.perform(request)
                    .andExpect(expectedResult)

    protected fun doPostRequest(content: String): MockHttpServletRequestBuilder =
            post(REQUEST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)

    protected fun doGetRequest(addedPath: String = ""): MockHttpServletRequestBuilder =
            get(REQUEST_URL + addedPath)

    protected fun doPutRequest(requestUrl: String = REQUEST_URL, content: String): MockHttpServletRequestBuilder =
            put(requestUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)

    protected fun doDeleteRequest(requestUrl: String = REQUEST_URL): MockHttpServletRequestBuilder =
            delete(requestUrl)

    protected fun assertServerError(serverError: ServerError,
                                    expectedErrorMessage: String,
                                    expectedPathParameters: Map<String, String>? = null,
                                    expectedRequestParameters: Map<String, String>? = null,
                                    expectedRequestBody: String? = null) {
        assertThat(serverError.errorMessage, `is`(expectedErrorMessage))
        assertThat(serverError.pathVariables, `is`(expectedPathParameters))
        assertThat(serverError.requestParameters, `is`(expectedRequestParameters))
        assertThat(serverError.requestBody, `is`(expectedRequestBody))
    }
}