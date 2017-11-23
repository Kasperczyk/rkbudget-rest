package de.kasperczyk.rkbudget.rest.budget

import de.kasperczyk.rkbudget.rest.AbstractRestControllerTest
import de.kasperczyk.rkbudget.rest.budget.entity.Budget
import de.kasperczyk.rkbudget.rest.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.testBudget
import de.kasperczyk.rkbudget.rest.testProfile
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BudgetRestController::class)
class BudgetRestControllerTest : AbstractRestControllerTest() {

    override val REQUEST_URL = "/profiles/${testProfile.id}/budgets"

    @MockBean
    private lateinit var budgetServiceMock: BudgetService

    private lateinit var jsonBudget: String

    @Before
    fun setUp() {
        jsonBudget = objectMapper.writeValueAsString(testBudget)
    }

    @Test
    fun `POST for a new budget creates it and returns the new budget and status code 201 (created)`() {
        `when`(budgetServiceMock.createBudget(profileId = testProfile.id, budget = testBudget)).thenReturn(testBudget)
        val result = performRequestForObject(doPostRequest(content = jsonBudget), status().isCreated, Budget::class.java)
        assertThat(result, `is`(testBudget))
        verify(budgetServiceMock).createBudget(profileId = testProfile.id, budget = testBudget)
    }

    @Test
    fun `POST for a non-existent profile returns status code 404 (not found) and a ServerError object`() {
        `when`(budgetServiceMock.createBudget(testProfile.id, testBudget))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        val result = performRequestForServerError(doPostRequest(content = jsonBudget), status().isNotFound)
        verify(budgetServiceMock).createBudget(profileId = testProfile.id, budget = testBudget)
        assertServerError(result,
                expectedErrorMessage = "Profile with id '${testProfile.id}' not found",
                expectedPathParameters = mapOf("profileId" to "${testProfile.id}"))
        verify(budgetServiceMock).createBudget(testProfile.id, testBudget)
    }

    @Test
    fun `POST without a valid body returns status code 400 (bad request) and a ServerError object`() {
        val result = performRequestForServerError(doPostRequest(content = "invalid"), status().isBadRequest)
        assertServerError(result, expectedErrorMessage = "Required request body of type ${Budget::class} is missing")
    }
}