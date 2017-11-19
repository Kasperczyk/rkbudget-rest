package de.kasperczyk.rkbudget.rest.tag

import de.kasperczyk.rkbudget.rest.AbstractRestControllerTest
import de.kasperczyk.rkbudget.rest.testProfile
import de.kasperczyk.rkbudget.rest.testTag
import org.junit.Before
import org.junit.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean

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
    fun `tests todo`() {

    }
}