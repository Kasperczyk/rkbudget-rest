package de.kasperczyk.rkbudget.rest.sharer

import de.kasperczyk.rkbudget.rest.AbstractService
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import org.springframework.stereotype.Service

@Service
class SharerService(val sharerRepository: SharerRepository,
                    profileService: ProfileService) : AbstractService(profileService) {


}