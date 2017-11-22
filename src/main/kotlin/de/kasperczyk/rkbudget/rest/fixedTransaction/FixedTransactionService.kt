package de.kasperczyk.rkbudget.rest.fixedTransaction

import de.kasperczyk.rkbudget.rest.AbstractService
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import org.springframework.stereotype.Service

@Service
class FixedTransactionService(val fixedTransactionRepository: FixedTransactionRepository,
                              profileService: ProfileService) : AbstractService(profileService) {


}