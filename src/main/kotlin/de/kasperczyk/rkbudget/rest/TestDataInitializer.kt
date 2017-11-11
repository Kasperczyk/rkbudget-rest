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
@org.springframework.context.annotation.Profile("test")
class TestDataInitializer(val profileService: ProfileService,
                          val accountService: AccountService) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val profiles = listOf(
                Profile(firstName = "Rene", lastName = "Kasperczyk", password = "rene123",
                        emailAddress = EmailAddress(fullAddress = "kasperczyk.rene@gmail.com")
                ),
                Profile(firstName = "Christina", lastName = "Kasperczyk", password = "chris123",
                        emailAddress = EmailAddress(fullAddress = "christina.kasperczyk@web.de")
                )
        )
        profiles.forEach { profileService.createProfile(it) }

        val accounts = listOf(
                Account(name = "Rene's Cash",
                        accountType = AccountType.CASH
                ),
                Account(name = "Rene's Giro Account",
                        accountType = AccountType.GIRO,
                        institute = "Some Bank", iban = "DE12 3456 7890 1234 5678 90",
                        expirationDate = LocalDate.of(2020, 12, 31)
                ),
                Account(name = "Rene's Boulder Card",
                        accountType = AccountType.CUSTOM
                )
        )
        accounts.forEach { accountService.createAccount(profiles.get(0).id, it) }

        println("\n>>> Test data initialization complete\n")
    }
}