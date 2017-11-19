package de.kasperczyk.rkbudget.rest.tag

import de.kasperczyk.rkbudget.rest.AbstractTransactionalIT
import de.kasperczyk.rkbudget.rest.testProfile
import org.junit.Test

class TagIT : AbstractTransactionalIT() {

    override val REQUEST_URL: String = "/profiles/${testProfile.id}/tags"

    @Test
    fun `todo tests`() {

    }
}