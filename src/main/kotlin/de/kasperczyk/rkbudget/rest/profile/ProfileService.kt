package de.kasperczyk.rkbudget.rest.profile

import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.profile.exception.DuplicateEmailAddressException
import de.kasperczyk.rkbudget.rest.profile.exception.ProfileNotFoundException
import org.springframework.stereotype.Service

@Service
class ProfileService(val profileRepository: ProfileRepository) {

    fun createProfile(profile: Profile): Profile =
            if (profileRepository.findByEmailAddress(profile.emailAddress) == null) {
                profileRepository.save(profile)
            } else {
                throw DuplicateEmailAddressException(profile.emailAddress)
            }

    fun getProfileByEmailAddress(profileEmailAddress: EmailAddress): Profile =
            profileRepository.findByEmailAddress(profileEmailAddress) ?:
                    throw ProfileNotFoundException(profileEmailAddress = profileEmailAddress)

    fun getProfileById(profileId: Long): Profile = validateProfile(profileId)

    fun updateProfile(updatedProfile: Profile) {
        val profile = validateProfile(updatedProfile.id)
        profile.apply {
            firstName = updatedProfile.firstName
            lastName = updatedProfile.lastName
            emailAddress = updatedProfile.emailAddress
            password = updatedProfile.password
        }
        profileRepository.save(profile)
    }

    fun deleteProfile(profileId: Long) {
        validateProfile(profileId)
        profileRepository.delete(profileId)
    }

    private fun validateProfile(profileId: Long): Profile =
            profileRepository.findOne(profileId) ?: throw ProfileNotFoundException(profileId = profileId)

    fun exists(profileId: Long): Boolean = profileRepository.exists(profileId)
}