package de.kasperczyk.rkbudget.rest.budget

import de.kasperczyk.rkbudget.rest.AbstractTransactionalIT
import de.kasperczyk.rkbudget.rest.testProfile
import org.junit.Test

class BudgetIT : AbstractTransactionalIT() {

    override val REQUEST_URL = "/profiles/${testProfile.id}/budgets"

    @Test
    fun `todo tests`() {

    }
}