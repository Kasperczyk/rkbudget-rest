package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.account.entity.Account
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import org.springframework.stereotype.Service

@Service
class AccountService(val accountRepository: AccountRepository,
                     val profileService: ProfileService) {

    fun createAccount(profileId: Long, account: Account): Account {
        val profile = profileService.getProfileById(profileId)
        account.profile = profile
        return accountRepository.save(account)
    }
}