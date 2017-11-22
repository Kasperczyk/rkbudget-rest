package de.kasperczyk.rkbudget.rest.budget

import de.kasperczyk.rkbudget.rest.AbstractDataJpaTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class BudgetRepositoryDataJpaTest : AbstractDataJpaTest() {

    @Autowired
    private lateinit var budgetRepository: BudgetRepository

    @Test
    fun `todo tests`() {

    }
}