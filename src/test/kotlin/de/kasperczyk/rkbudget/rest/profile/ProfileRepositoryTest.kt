package de.kasperczyk.rkbudget.rest.profile

import de.kasperczyk.rkbudget.rest.AbstractDataJpaTest
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.testEmailAddress
import de.kasperczyk.rkbudget.rest.testProfile
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class ProfileRepositoryTest : AbstractDataJpaTest() {

    @Autowired
    private lateinit var profileRepository: ProfileRepository

    @Test
    fun `findByEmailAddress with a registered email address returns the associated profile`() {
        val profile = Profile(
                firstName = testProfile.firstName,
                lastName = testProfile.lastName,
                emailAddress = testProfile.emailAddress,
                password = testProfile.password
        )
        entityManager.persist(profile)
        val result = profileRepository.findByEmailAddress(testEmailAddress)
        assertThat(result, `is`(profile))
    }

    @Test
    fun `findByEmailAddress with a non-existent email address returns null`() {
        val result = profileRepository.findByEmailAddress(EmailAddress(fullAddress = "non-existent"))
        assertThat(result, `is`(nullValue()))
    }
}