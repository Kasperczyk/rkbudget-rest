package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.AbstractTransactionalIT
import de.kasperczyk.rkbudget.rest.testProfile
import org.junit.Test

class AccountIT : AbstractTransactionalIT() {

    override val REQUEST_URL = "/profiles/$testProfile.id}/accounts"

    @Test
    fun `todo tests`() {

    }
}