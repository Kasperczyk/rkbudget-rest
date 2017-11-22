package de.kasperczyk.rkbudget.rest.fixedTransaction

import de.kasperczyk.rkbudget.rest.AbstractTransactionalIT
import de.kasperczyk.rkbudget.rest.testProfile
import org.junit.Test

class FixedTransactionRestControllerTest : AbstractTransactionalIT() {

    override val REQUEST_URL = "/profiles/${testProfile.id}/fixedTransactions"

    @Test
    fun `todo tests`() {

    }
}