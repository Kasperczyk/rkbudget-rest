package de.kasperczyk.rkbudget.rest.variableTransaction

import de.kasperczyk.rkbudget.rest.AbstractTransactionalIT
import de.kasperczyk.rkbudget.rest.testProfile
import org.junit.Test

class TransactionIT : AbstractTransactionalIT() {

    override val REQUEST_URL = "/profiles/${testProfile.id}/transactions"

    @Test
    fun `todo tests`() {

    }
}