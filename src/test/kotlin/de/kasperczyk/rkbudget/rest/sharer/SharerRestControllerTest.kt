package de.kasperczyk.rkbudget.rest.sharer

import de.kasperczyk.rkbudget.rest.AbstractRestControllerTest
import de.kasperczyk.rkbudget.rest.testProfile
import org.junit.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean

@WebMvcTest(SharerRestController::class)
class SharerRestControllerTest : AbstractRestControllerTest() {

    override val REQUEST_URL = "/profiles/${testProfile.id}/sharers"

    @MockBean
    private lateinit var sharerServiceMock: SharerService

    private lateinit var jsonSharer: String

    @Test
    fun `todo tests`() {

    }
}