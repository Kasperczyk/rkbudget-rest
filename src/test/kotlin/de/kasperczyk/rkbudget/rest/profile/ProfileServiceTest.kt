package de.kasperczyk.rkbudget.rest.profile

import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.profile.exception.DuplicateEmailAddressException
import de.kasperczyk.rkbudget.rest.profile.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.testEmailAddress
import de.kasperczyk.rkbudget.rest.testProfile
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class ProfileServiceTest {

    private lateinit var profileService: ProfileService

    @Mock
    private lateinit var profileRepositoryMock: ProfileRepository

    @Rule
    @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setup() {
        profileService = ProfileService(profileRepositoryMock)
    }

    @Test
    fun `creating a profile saves it to the database`() {
        `when`(profileRepositoryMock.findByEmailAddress(testEmailAddress)).thenReturn(null)
        `when`(profileRepositoryMock.save(testProfile)).thenReturn(testProfile)
        profileService.createProfile(testProfile)
        verify(profileRepositoryMock).findByEmailAddress(testEmailAddress)
        verify(profileRepositoryMock).save(testProfile)
    }

    @Test
    fun `trying to create a profile with an already registered email address throws a DuplicateEmailAddressException`() {
        `when`(profileRepositoryMock.findByEmailAddress(testEmailAddress)).thenReturn(testProfile)
        expectedException.expect(DuplicateEmailAddressException::class.java)
        expectedException.expectMessage("A profile with email address '${testEmailAddress.fullAddress}' " +
                "has already been registered")
        profileService.createProfile(testProfile)
        verify(profileRepositoryMock).findByEmailAddress(testEmailAddress)
    }

    @Test
    fun `getting a profile with a registered email address returns the associated profile`() {
        `when`(profileRepositoryMock.findByEmailAddress(testEmailAddress)).thenReturn(testProfile)
        val profile = profileService.getProfileByEmailAddress(testEmailAddress)
        assertThat(profile, `is`(testProfile))
        verify(profileRepositoryMock).findByEmailAddress(testEmailAddress)
    }

    @Test
    fun `trying to get a profile with an unregistered email address throws a ProfileNotFoundException`() {
        `when`(profileRepositoryMock.findByEmailAddress(testEmailAddress)).thenReturn(null)
        expectedException.expect(ProfileNotFoundException::class.java)
        expectedException.expectMessage("Profile with email address '${testEmailAddress.fullAddress}' not found")
        profileService.getProfileByEmailAddress(testEmailAddress)
        verify(profileRepositoryMock).findByEmailAddress(testEmailAddress)
    }

    @Test
    fun `updating an existings profile updates that profile in the database`() {
        val updatedProfile = Profile(
                firstName = "Christina",
                lastName = "Kasperczyk",
                emailAddress = EmailAddress(fullAddress = "christina.kasperczyk@web.de"),
                password = "geheim"
        )
        assertThat(updatedProfile, `is`(not(testProfile)))
        `when`(profileRepositoryMock.findOne(updatedProfile.id)).thenReturn(testProfile.copy())
        profileService.updateProfile(updatedProfile.id, updatedProfile)
        verify(profileRepositoryMock).findOne(updatedProfile.id)
        verify(profileRepositoryMock).save(updatedProfile)
    }

    @Test
    fun `trying to update a non-existent profile throws a ProfileNotFoundException`() {
        val updatedProfile = Profile(id = 1)
        `when`(profileRepositoryMock.findOne(updatedProfile.id)).thenReturn(null)
        expectedException.expect(ProfileNotFoundException::class.java)
        expectedException.expectMessage("Profile with id '${updatedProfile.id}' not found")
        profileService.updateProfile(updatedProfile.id, updatedProfile)
        verify(profileRepositoryMock).findOne(updatedProfile.id)
    }

    @Test
    fun `deleting an existing profile deletes that profile from the database`() {
        `when`(profileRepositoryMock.findOne(testProfile.id)).thenReturn(testProfile)
        profileService.deleteProfile(testProfile.id)
        verify(profileRepositoryMock).delete(testProfile.id)
    }

    @Test
    fun `trying to delete a non-existent profile throws a ProfileNotFoundException`() {
        `when`(profileRepositoryMock.findOne(testProfile.id)).thenReturn(null)
        expectedException.expect(ProfileNotFoundException::class.java)
        expectedException.expectMessage("Profile with id '${testProfile.id}' not found")
        profileService.deleteProfile(testProfile.id)
    }
}