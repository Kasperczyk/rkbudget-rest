package de.kasperczyk.rkbudget.rest.profile

import de.kasperczyk.rkbudget.rest.AbstractTransactionalIntegrationTest
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.testEmailAddress
import de.kasperczyk.rkbudget.rest.testProfile
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class ProfileRepositoryIntegrationTest : AbstractTransactionalIntegrationTest() {

    @Autowired
    private lateinit var profileRepository: ProfileRepository

    @Test
    fun `findByEmailAddress with a registered email address returns the associated profile`() {
        val profile = profileRepository.findByEmailAddress(testEmailAddress)
        assertThat(profile, `is`(testProfile.copy(id = 1)))
    }

    @Test
    fun `findByEmailAddress with a non-existent email address returns null`() {
        val profile = profileRepository.findByEmailAddress(EmailAddress(fullAddress = "non-existent"))
        assertThat(profile, `is`(nullValue()))
    }

    @Test
    fun `findById with an existing id returns the associated profile`() {
        val profile = profileRepository.findById(1)
        assertThat(profile, `is`(testProfile.copy(id = 1)))
    }

    @Test
    fun `findById with a non-existent id returns null`() {
        val profile = profileRepository.findById(-1)
        assertThat(profile, `is`(nullValue()))
    }
}