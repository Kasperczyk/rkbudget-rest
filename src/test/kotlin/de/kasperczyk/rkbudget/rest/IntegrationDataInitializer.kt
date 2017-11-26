package de.kasperczyk.rkbudget.rest

import de.kasperczyk.rkbudget.rest.account.AccountService
import de.kasperczyk.rkbudget.rest.account.entity.*
import de.kasperczyk.rkbudget.rest.budget.BudgetService
import de.kasperczyk.rkbudget.rest.budget.entity.Budget
import de.kasperczyk.rkbudget.rest.fixedTransaction.FixedTransactionService
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.sharer.SharerService
import de.kasperczyk.rkbudget.rest.sharer.entity.Sharer
import de.kasperczyk.rkbudget.rest.tag.TagService
import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import de.kasperczyk.rkbudget.rest.variableTransaction.TransactionService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDate

@Component
@org.springframework.context.annotation.Profile("integration")
class IntegrationDataInitializer(val profileService: ProfileService,
                                 val accountService: AccountService,
                                 val tagService: TagService,
                                 val budgetService: BudgetService,
                                 val sharerService: SharerService,
                                 val fixedTransactionService: FixedTransactionService,
                                 val transactionService: TransactionService) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        profiles.addAll(
                listOf(
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
        )
        profiles.forEach { profileService.createProfile(it) }

        accounts.addAll(
                listOf(
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
        )
        accounts.forEach { accountService.createAccount(profiles[0].id, it) }

        tags.addAll(
                listOf(
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
        )
        tags.forEach { tagService.createTag(profiles[0].id, it) }

        budgets.addAll(
                listOf(
                        Budget(
                                name = "Food Budget",
                                limit = BigDecimal.valueOf(350),
                                thresholdPercentage = 80,
                                sendEmail = true,
                                tags = setOf(tags[0])
                        ),
                        Budget(
                                name = "Fitness Budget",
                                limit = BigDecimal.valueOf(150),
                                thresholdPercentage = 85,
                                sendEmail = true,
                                tags = setOf(tags[1])
                        )
                )
        )
        budgets.forEach { budgetService.createBudget(profiles[0].id, it) }

        sharers.addAll(
                listOf(
                        Sharer(
                                firstName = "Christina",
                                lastName = "Kasperczyk",
                                emailAddress = EmailAddress(fullAddress = "christina.kasperczyk@web.de")
                        )
                )
        )
//        sharers.forEach { sharerService.createSharer(profiles[0].id, it) }

        println("\n>>> Integration data initialization complete\n")
    }
}