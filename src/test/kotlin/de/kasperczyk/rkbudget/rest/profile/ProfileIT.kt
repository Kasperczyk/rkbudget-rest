package de.kasperczyk.rkbudget.rest.profile

import de.kasperczyk.rkbudget.rest.AbstractTransactionalIT
import de.kasperczyk.rkbudget.rest.exception.ServerError
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.profiles
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
                password = "Selina"
        )
        val profile = restTemplate.postForObject(REQUEST_URL, newProfile, Profile::class.java)
        assertThat(profile.id, `is`(profiles.count() + 1L))
        assertThat(profile, `is`(newProfile.copy(id = 3)))
    }

    @Test
    fun `trying to create a profile with an already registered email address returns a ServerError object`() {
        val newProfile = Profile(emailAddress = profiles[0].emailAddress)
        val serverError = restTemplate.postForObject(REQUEST_URL, newProfile, ServerError::class.java)
        assertThat(serverError.errorMessage, `is`("A profile with email address '${profiles[0].emailAddress.fullAddress}' " +
                "has already been registered"))
        assertThat(serverError.requestBody, `is`(objectMapper.writeValueAsString(newProfile)))
    }

    @Test
    fun `todo validation`() {

    }
}