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
import java.time.LocalDate

@Component
@org.springframework.context.annotation.Profile("integration")
class IntegrationDataInitializer(val profileService: ProfileService,
                                 val accountService: AccountService) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val profiles = listOf(
                testProfile,
                Profile(firstName = "Christina", lastName = "Kasperczyk", password = "secret",
                        emailAddress = EmailAddress(fullAddress = "christina.kasperczyk@web.de")
                )
        )
        profiles.forEach { profileService.createProfile(it) }

        val accounts = listOf(
                testAccount,
                Account(
                        name = "Rene's Savings Account",
                        accountType = AccountType.SAVINGS,
                        institute = "Some Bank",
                        iban = "DE23 4567 8901 2345 6789 01",
                        expirationDate = LocalDate.of(2021, 1, 31),
                        profile = profiles[0]
                ),
                Account(
                        name = "Rene's Credit Card",
                        accountType = AccountType.CREDIT,
                        institute = "Some Bank",
                        creditCardNumber = "1234 5678 9012 3456",
                        expirationDate = LocalDate.of(2019, 2, 28),
                        profile = profiles[0]
                ),
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