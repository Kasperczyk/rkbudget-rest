package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.account.entity.*
import de.kasperczyk.rkbudget.rest.exception.EntityNotFoundException
import de.kasperczyk.rkbudget.rest.exception.ProfileNotFoundException
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

    fun getAllAccountsForProfile(profileId: Long): List<Account> {
        validateProfile(profileId)
        return accountRepository.findAllByProfileId(profileId)
    }

    fun updateAccount(accountId: Long, updatedAccount: Account) {

        fun validateAccountType(account: Account, updatedAccount: Account) {
            if (account.accountType != updatedAccount.accountType) {
                throw IllegalStateException("Account types differ, but need to be equal. The account in the database is " +
                        "of type ${account.accountType}; the new type is ${updatedAccount.accountType}")
            }
        }

        validateProfile(updatedAccount.profile.id)
        validateAccount(accountId)
        val account = accountRepository.findOne(accountId)
        validateAccountType(account, updatedAccount)
        when (account) {
            is CustomAccount -> {
                updatedAccount as CustomAccount
                account.expirationDate = updatedAccount.expirationDate
            }
            is ExpirableAccount -> {
                updatedAccount as ExpirableAccount
                account.expirationDate = updatedAccount.expirationDate
                when (account) {
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
            }
        }
        account.name = updatedAccount.name
        account.profile = updatedAccount.profile
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
            throw EntityNotFoundException(accountId, "accountId")
        }
    }
}