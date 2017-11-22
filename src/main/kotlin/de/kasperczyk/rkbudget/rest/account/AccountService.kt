package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.AbstractService
import de.kasperczyk.rkbudget.rest.account.entity.*
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import org.springframework.stereotype.Service

@Service
class AccountService(val accountRepository: AccountRepository,
                     profileService: ProfileService) : AbstractService(profileService) {

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

        fun updateFields(account: Account, updatedAccount: Account) {
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
        }

        validateProfile(updatedAccount.profile.id)
        validateEntity(accountRepository, accountId, "accountId")
        val account = accountRepository.findOne(accountId)
        validateAccountType(account, updatedAccount)
        updateFields(account, updatedAccount)
        accountRepository.save(account)
    }

    fun deleteAccount(profileId: Long, accountId: Long) {
        validateProfile(profileId)
        validateEntity(accountRepository, accountId, "accountId")
        accountRepository.delete(accountId)
    }
}