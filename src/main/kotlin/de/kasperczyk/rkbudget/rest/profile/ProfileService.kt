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

    fun getProfileById(profileId: Long): Profile =
            profileRepository.findById(profileId) ?:
                    throw ProfileNotFoundException(profileId = profileId)

    fun updateProfile(profileId: Long, updatedProfile: Profile) {
        val profile = profileRepository.findOne(profileId) ?:
                throw ProfileNotFoundException(profileId = profileId)
        profile.apply {
            firstName = updatedProfile.firstName
            lastName = updatedProfile.lastName
            emailAddress = updatedProfile.emailAddress
            password = updatedProfile.password
        }
        profileRepository.save(profile)
    }

    fun deleteProfile(profileId: Long) {
        profileRepository.findOne(profileId) ?:
                throw ProfileNotFoundException(profileId = profileId)
        profileRepository.delete(profileId)
    }
}