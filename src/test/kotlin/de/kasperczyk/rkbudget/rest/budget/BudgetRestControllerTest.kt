package de.kasperczyk.rkbudget.rest.budget

import de.kasperczyk.rkbudget.rest.AbstractRestControllerTest
import de.kasperczyk.rkbudget.rest.testProfile
import org.junit.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean

@WebMvcTest(BudgetRestController::class)
class BudgetRestControllerTest : AbstractRestControllerTest() {

    override val REQUEST_URL = "/profiles/${testProfile.id}/budgets"

    @MockBean
    private lateinit var budgetServiceMock: BudgetService

    private lateinit var jsonBudget: String

    @Test
    fun `todo tests`() {

    }
}