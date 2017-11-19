package de.kasperczyk.rkbudget.rest.profile

import de.kasperczyk.rkbudget.rest.AbstractTransactionalIT
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ProfileIT : AbstractTransactionalIT() {

    override val REQUEST_URL = "/profiles"

    @Test
    fun `creating a new profile saves it to the database and returns it`() {
        val newProfile = Profile(
                firstName = "Bruce",
                lastName = "Wayne",
                emailAddress = EmailAddress(fullAddress = "bruce.wayne@wayne-enterprises.com"),
                password = "catwoman"
        )
        val profile = restTemplate.postForObject(REQUEST_URL, newProfile, Profile::class.java)
        assertThat(profile.id, `is`(3L))
        assertThat(profile, `is`(newProfile.copy(id = 3)))
    }
}