package de.kasperczyk.rkbudget.rest.exception

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class EntityNotFoundExceptionTest {

    @Test
    fun `EntityNotFoundException builds its message correctly`() {
        val message = EntityNotFoundException(1, "accountId").message
        assertThat(message, `is`("Account with id '1' not found"))
    }
}