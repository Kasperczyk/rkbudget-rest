package de.kasperczyk.rkbudget.rest.variableTransaction

import de.kasperczyk.rkbudget.rest.profile.ProfileService
import org.springframework.stereotype.Service

@Service
class TransactionService(transactionRepository: TransactionRepository,
                         val profileService: ProfileService) {
}