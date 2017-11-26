package de.kasperczyk.rkbudget.rest.sharer

import de.kasperczyk.rkbudget.rest.AbstractService
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import de.kasperczyk.rkbudget.rest.sharer.entity.Sharer
import org.springframework.stereotype.Service

@Service
class SharerService(val sharerRepository: SharerRepository,
                    profileService: ProfileService) : AbstractService(profileService) {

    fun createSharer(profileId: Long, sharer: Sharer): Sharer = Sharer()

    fun getAllSharersForProfile(profileId: Long): List<Sharer> = sharerRepository.findAllByProfileId(profileId)
}