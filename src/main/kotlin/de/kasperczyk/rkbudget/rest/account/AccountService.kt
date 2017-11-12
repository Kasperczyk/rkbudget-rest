package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.account.entity.Account
import de.kasperczyk.rkbudget.rest.account.exception.AccountNotFoundException
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import de.kasperczyk.rkbudget.rest.profile.exception.ProfileNotFoundException
import org.springframework.stereotype.Service

@Service
class AccountService(val accountRepository: AccountRepository,
                     val profileService: ProfileService) {

    fun createAccount(profileId: Long, account: Account): Account {
        val profile = profileService.getProfileById(profileId)
        account.profile = profile
        return accountRepository.save(account)
    }

    fun getAllAccountsForProfile(profileId: Long): List<Account> {
        verifyProfile(profileId)
        return accountRepository.findAllByProfileId(profileId)
    }

    fun updateAccount(accountId: Long, updatedAccount: Account) {
        verifyProfile(updatedAccount.profile.id)
        verifyAccount(accountId)
        val account = accountRepository.findOne(accountId)
        account.apply {
            name = updatedAccount.name
            institute = updatedAccount.institute
            iban = updatedAccount.iban
            creditCardNumber = updatedAccount.creditCardNumber
            expirationDate = updatedAccount.expirationDate
            profile = updatedAccount.profile
        }
        accountRepository.save(account)
    }

    fun deleteAccount(profileId: Long, accountId: Long) {
        verifyProfile(profileId)
        verifyAccount(accountId)
        accountRepository.delete(accountId)
    }

    private fun verifyProfile(profileId: Long) {
        if (!profileService.exists(profileId)) {
            throw ProfileNotFoundException(profileId = profileId)
        }
    }

    private fun verifyAccount(accountId: Long) {
        if (!accountRepository.exists(accountId)) {
            throw AccountNotFoundException(accountId)
        }
    }
}