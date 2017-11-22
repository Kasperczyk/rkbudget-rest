package de.kasperczyk.rkbudget.rest.budget

import de.kasperczyk.rkbudget.rest.profile.ProfileService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BudgetServiceTest {

    private lateinit var budgetService: BudgetService

    @Mock
    private lateinit var budgetRepositoryMock: BudgetRepository

    @Mock
    private lateinit var profileServiceMock: ProfileService

    @Rule
    @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setUp() {
        budgetService = BudgetService(budgetRepositoryMock, profileServiceMock)
    }

    @Test
    fun `todo tests`() {

    }
}