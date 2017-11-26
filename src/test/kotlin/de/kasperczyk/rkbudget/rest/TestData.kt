package de.kasperczyk.rkbudget.rest

import de.kasperczyk.rkbudget.rest.account.entity.Account
import de.kasperczyk.rkbudget.rest.account.entity.GiroAccount
import de.kasperczyk.rkbudget.rest.budget.entity.Budget
import de.kasperczyk.rkbudget.rest.fixedTransaction.entity.FixedExpense
import de.kasperczyk.rkbudget.rest.fixedTransaction.entity.FixedTransaction
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.sharer.entity.Sharer
import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import de.kasperczyk.rkbudget.rest.variableTransaction.entity.VariableExpense
import de.kasperczyk.rkbudget.rest.variableTransaction.entity.VariableTransaction
import org.javamoney.moneta.Money
import java.math.BigDecimal
import java.time.LocalDate

val testEmailAddress = EmailAddress(fullAddress = "kasperczyk.rene@gmail.com")

val profiles: MutableList<Profile> = mutableListOf()
val accounts: MutableList<Account> = mutableListOf()
val tags: MutableList<Tag> = mutableListOf()
val budgets: MutableList<Budget> = mutableListOf()
val sharers: MutableList<Sharer> = mutableListOf()
val fixedTransactions: MutableList<FixedTransaction> = mutableListOf()
val transactions: MutableList<VariableTransaction> = mutableListOf()

val testProfile = Profile(
        firstName = "Rene",
        lastName = "Kasperczyk",
        emailAddress = testEmailAddress,
        password = "secret"
)

val testAccount = GiroAccount(
        name = "My Giro Account",
        profile = testProfile,
        expirationDate = LocalDate.of(2022, 12, 31),
        institute = "Some bank",
        iban = "DE 12345678 1234567890 12"
)

val testTag = Tag(
        name = "Food",
        profile = testProfile
)

val testBudget = Budget(
        name = "Food Budget",
        limit = BigDecimal.valueOf(350),
        thresholdPercentage = 80,
        sendEmail = true,
        tags = setOf(testTag)
)

val testSharer = Sharer(
        firstName = "Christina",
        lastName = "Kasperczyk",
        emailAddress = EmailAddress(fullAddress = "christina.kasperczyk@web.de")
)

val testFixedTransaction = FixedExpense(
        // todo
)

val testTransaction = VariableExpense(
        date = LocalDate.of(2017, 11, 24),
        amount = Money.of(73.56, EURO),
        title = "Food",
        tags = setOf(testTag),
        sharedWith = setOf(testSharer)
)