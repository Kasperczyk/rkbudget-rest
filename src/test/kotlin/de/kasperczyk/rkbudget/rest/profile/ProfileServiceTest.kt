package de.kasperczyk.rkbudget.rest.profile

import de.kasperczyk.rkbudget.rest.exception.DuplicateEmailAddressException
import de.kasperczyk.rkbudget.rest.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
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
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
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
    fun `creating a profile saves it to the database and returns it`() {
        `when`(profileRepositoryMock.findByEmailAddress(testEmailAddress)).thenReturn(null)
        `when`(profileRepositoryMock.save(testProfile)).thenReturn(testProfile)
        val result = profileService.createProfile(testProfile)
        assertThat(result, `is`(testProfile))
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
        verify(profileRepositoryMock, never()).save(testProfile)
    }

    @Test
    fun `getting a profile with a registered email address loads the associated profile from the database and returns it`() {
        `when`(profileRepositoryMock.findByEmailAddress(testEmailAddress)).thenReturn(testProfile)
        val result = profileService.getProfileByEmailAddress(testEmailAddress)
        assertThat(result, `is`(testProfile))
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
    fun `getting a profile with an existing id loads the associated profile from the database and returns it`() {
        `when`(profileRepositoryMock.findOne(testProfile.id)).thenReturn(testProfile)
        val result = profileService.getProfileById(testProfile.id)
        assertThat(result, `is`(testProfile))
        verify(profileRepositoryMock).findOne(testProfile.id)
    }

    @Test
    fun `trying to get a profile with a non-existent id throws a ProfileNotFoundException`() {
        `when`(profileRepositoryMock.findOne(testProfile.id)).thenReturn(null)
        expectedException.expect(ProfileNotFoundException::class.java)
        expectedException.expectMessage("Profile with id '${testProfile.id}' not found")
        profileService.getProfileById(testProfile.id)
        verify(profileRepositoryMock).findOne(testProfile.id)
    }

    @Test
    fun `updating an existing profile updates that profile in the database`() {
        val updatedProfile = Profile(
                id = 1,
                firstName = "Christina",
                lastName = "Kasperczyk",
                emailAddress = EmailAddress(fullAddress = "christina.kasperczyk@web.de"),
                password = "password"
        )
        assertThat(updatedProfile, `is`(not(testProfile.copy(id = 1))))
        `when`(profileRepositoryMock.findOne(updatedProfile.id)).thenReturn(testProfile.copy(id = 1))
        profileService.updateProfile(updatedProfile)
        verify(profileRepositoryMock).findOne(updatedProfile.id)
        verify(profileRepositoryMock).save(updatedProfile)
    }

    @Test
    fun `trying to update a non-existent profile throws a ProfileNotFoundException`() {
        val updatedProfile = Profile(id = 1)
        `when`(profileRepositoryMock.findOne(updatedProfile.id)).thenReturn(null)
        expectedException.expect(ProfileNotFoundException::class.java)
        expectedException.expectMessage("Profile with id '${updatedProfile.id}' not found")
        profileService.updateProfile(updatedProfile)
        verify(profileRepositoryMock).findOne(updatedProfile.id)
        verify(profileRepositoryMock, never()).save(updatedProfile)
    }

    @Test
    fun `deleting an existing profile deletes that profile from the database`() {
        `when`(profileRepositoryMock.findOne(testProfile.id)).thenReturn(testProfile)
        profileService.deleteProfile(testProfile.id)
        verify(profileRepositoryMock).findOne(testProfile.id)
        verify(profileRepositoryMock).delete(testProfile.id)
    }

    @Test
    fun `trying to delete a non-existent profile throws a ProfileNotFoundException`() {
        `when`(profileRepositoryMock.findOne(testProfile.id)).thenReturn(null)
        expectedException.expect(ProfileNotFoundException::class.java)
        expectedException.expectMessage("Profile with id '${testProfile.id}' not found")
        profileService.deleteProfile(testProfile.id)
        verify(profileRepositoryMock).findOne(testProfile.id)
        verify(profileRepositoryMock, never()).delete(testProfile.id)
    }

    @Test
    fun `exists returns true if the profile exists`() {
        `when`(profileRepositoryMock.exists(testProfile.id)).thenReturn(true)
        val result = profileService.exists(testProfile.id)
        assertThat(result, `is`(true))
        verify(profileRepositoryMock).exists(testProfile.id)
    }

    @Test
    fun `exists returns false if the profile does not exist`() {
        `when`(profileRepositoryMock.exists(testProfile.id)).thenReturn(false)
        val result = profileService.exists(testProfile.id)
        assertThat(result, `is`(false))
        verify(profileRepositoryMock).exists(testProfile.id)
    }
}