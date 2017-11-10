package de.kasperczyk.rkbudget.rest.profile.exception

import de.kasperczyk.rkbudget.rest.testEmailAddress
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class ProfileNotFoundExceptionTest {

    @Rule
    @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    @Test
    fun `creating a ProfileNotFoundException throws an IllegalStateException if no parameter is set`() {
        expectedException.expect(IllegalStateException::class.java)
        expectedException.expectMessage("Either 'profileEmailAddress' or 'profileId' must not be null")
        ProfileNotFoundException()
    }

    @Test
    fun `creating a ProfileNotFoundException throws an IllegalStateException if both parameters are set`() {
        expectedException.expect(IllegalStateException::class.java)
        expectedException.expectMessage("Either 'profileEmailAddress' or 'profileId' must not be null")
        ProfileNotFoundException(testEmailAddress, 1L)
    }
}