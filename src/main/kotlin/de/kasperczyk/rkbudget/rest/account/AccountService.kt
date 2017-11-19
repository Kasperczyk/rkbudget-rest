package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.account.entity.Account
import de.kasperczyk.rkbudget.rest.account.entity.BankAccount
import de.kasperczyk.rkbudget.rest.account.entity.CreditAccount
import de.kasperczyk.rkbudget.rest.account.entity.ExpirableAccount
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
        validateProfile(profileId)
        return accountRepository.findAllByProfileId(profileId)
    }

    fun updateAccount(accountId: Long, updatedAccount: Account) {
        validateProfile(updatedAccount.profile.id)
        validateAccount(accountId)
        val account = accountRepository.findOne(accountId)
        account.apply {
            when (account) {
                is ExpirableAccount -> {
                    updatedAccount as ExpirableAccount
                    account.expirationDate = updatedAccount.expirationDate
                }
                is BankAccount -> {
                    updatedAccount as BankAccount
                    account.institute = updatedAccount.institute
                    account.iban = updatedAccount.iban
                }
                is CreditAccount -> {
                    updatedAccount as CreditAccount
                    account.issuer = updatedAccount.issuer
                    account.creditCardNumber = updatedAccount.creditCardNumber
                }
            }
            name = updatedAccount.name
            profile = updatedAccount.profile
        }
        accountRepository.save(account)
    }

    fun deleteAccount(profileId: Long, accountId: Long) {
        validateProfile(profileId)
        validateAccount(accountId)
        accountRepository.delete(accountId)
    }

    private fun validateProfile(profileId: Long) {
        if (!profileService.exists(profileId)) {
            throw ProfileNotFoundException(profileId = profileId)
        }
    }

    private fun validateAccount(accountId: Long) {
        if (!accountRepository.exists(accountId)) {
            throw AccountNotFoundException(accountId)
        }
    }
}