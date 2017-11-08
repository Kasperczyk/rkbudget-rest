package de.kasperczyk.rkbudget.rest.profile

import org.springframework.stereotype.Service

@Service
class ProfileService(val profileRepository: ProfileRepository) {

    fun getProfileByEmailAddress(profileEmailAddress: EmailAddress): Profile =
            profileRepository.findByEmailAddress(profileEmailAddress) ?:
                    throw ProfileNotFoundException(profileEmailAddress)
}