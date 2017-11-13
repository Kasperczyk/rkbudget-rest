package de.kasperczyk.rkbudget.rest

import de.kasperczyk.rkbudget.rest.account.AccountService
import de.kasperczyk.rkbudget.rest.account.entity.Account
import de.kasperczyk.rkbudget.rest.account.entity.AccountType
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
@org.springframework.context.annotation.Profile("test")
class TestDataInitializer(val profileService: ProfileService,
                          val accountService: AccountService) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val profiles = listOf(
                testProfile,
                Profile(firstName = "Christina", lastName = "Kasperczyk", password = "chris123",
                        emailAddress = EmailAddress(fullAddress = "christina.kasperczyk@web.de")
                )
        )
        profiles.forEach { profileService.createProfile(it) }

        val accounts = listOf(
                testAccount,
                Account(name = "Rene's Cash",
                        accountType = AccountType.CASH
                ),
                Account(name = "Rene's Boulder Card",
                        accountType = AccountType.CUSTOM
                )
        )
        accounts.forEach { accountService.createAccount(profiles.get(0).id, it) }

        println("\n>>> Test data initialization complete\n")
    }
}