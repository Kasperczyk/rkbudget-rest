package de.kasperczyk.rkbudget.rest.sharer

import de.kasperczyk.rkbudget.rest.AbstractTransactionalIT
import de.kasperczyk.rkbudget.rest.testProfile
import org.junit.Test

class SharerIT : AbstractTransactionalIT() {

    override val REQUEST_URL = "/profiles/${testProfile.id}/sharers"

    @Test
    fun `todo tests`() {

    }
}