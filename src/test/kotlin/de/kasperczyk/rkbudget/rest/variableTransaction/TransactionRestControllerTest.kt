package de.kasperczyk.rkbudget.rest.variableTransaction

import de.kasperczyk.rkbudget.rest.AbstractRestControllerTest
import de.kasperczyk.rkbudget.rest.testProfile
import org.junit.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean

@WebMvcTest(TransactionRestController::class)
class TransactionRestControllerTest : AbstractRestControllerTest() {

    override val REQUEST_URL = "profiles/${testProfile.id}/transactions"

    @MockBean
    private lateinit var transactionServiceMock: TransactionService

    private lateinit var jsonTransaction: String

    @Test
    fun `todo tests`() {

    }
}