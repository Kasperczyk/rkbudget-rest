package de.kasperczyk.rkbudget.rest.profile

import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class ProfileService(val profileRepository: ProfileRepository) {

    fun getProfileById(profileId: Long): Profile =
            profileRepository.findById(profileId) ?: throw RuntimeException("Profile with id $profileId not found")
}