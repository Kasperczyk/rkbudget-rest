package de.kasperczyk.rkbudget.rest

import de.kasperczyk.rkbudget.rest.account.AccountService
import de.kasperczyk.rkbudget.rest.account.entity.*
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.tag.TagService
import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
@org.springframework.context.annotation.Profile("dev")
class DevDataInitializer(val profileService: ProfileService,
                         val accountService: AccountService,
                         val tagService: TagService) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val profiles = listOf(
                Profile(
                        firstName = "Rene",
                        lastName = "Kasperczyk",
                        emailAddress = EmailAddress(fullAddress = "kasperczyk.rene@gmail.com"),
                        password = "secret"
                ),
                Profile(firstName = "Christina",
                        lastName = "Kasperczyk",
                        emailAddress = EmailAddress(fullAddress = "christina.kasperczyk@web.de"),
                        password = "secret"
                )
        )
        profiles.forEach { profileService.createProfile(it) }

        val accounts = listOf(
                GiroAccount(
                        name = "Rene's Giro Account",
                        institute = "Some Bank",
                        iban = "DE12 3456 7890 1234 5678 90",
                        expirationDate = LocalDate.of(2020, 12, 31),
                        profile = profiles[0]
                ),
                SavingsAccount(
                        name = "Rene's Savings Account",
                        institute = "Some Bank",
                        iban = "DE23 4567 8901 2345 6789 01",
                        expirationDate = LocalDate.of(2021, 1, 31),
                        profile = profiles[0]
                ),
                CreditAccount(
                        name = "Rene's Credit Card",
                        issuer = "Some Bank",
                        creditCardNumber = "1234 5678 9012 3456",
                        expirationDate = LocalDate.of(2019, 2, 28),
                        profile = profiles[0]
                ),
                CashAccount(
                        name = "Rene's Cash",
                        profile = profiles[0]
                ),
                CustomAccount(
                        name = "Rene's Boulder Card",
                        profile = profiles[0]
                )
        )
        accounts.forEach { accountService.createAccount(profiles[0].id, it) }

        val tags = listOf(
                Tag(
                        name = "Food",
                        profile = profiles[0]
                ),
                Tag(
                        name = "Fitness",
                        profile = profiles[0]
                ),
                Tag(
                        name = "Clothes",
                        profile = profiles[0]
                )
        )
        tags.forEach { tagService.createTag(profiles[0].id, it) }

        println("\n>>> Development test data initialization complete\n")
    }
}