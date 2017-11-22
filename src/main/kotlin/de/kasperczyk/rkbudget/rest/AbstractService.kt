package de.kasperczyk.rkbudget.rest

import de.kasperczyk.rkbudget.rest.exception.EntityNotFoundException
import de.kasperczyk.rkbudget.rest.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service

@Service
abstract class AbstractService(val profileService: ProfileService) {

    fun validateProfile(profileId: Long) {
        if (!profileService.exists(profileId)) {
            throw ProfileNotFoundException(profileId = profileId)
        }
    }

    fun <T> validateEntity(repository: CrudRepository<T, Long>, entityId: Long, key: String) {
        if (!repository.exists(entityId)) {
            throw EntityNotFoundException(entityId, key)
        }
    }
}