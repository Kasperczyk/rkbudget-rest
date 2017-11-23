package de.kasperczyk.rkbudget.rest

import de.kasperczyk.rkbudget.rest.account.entity.GiroAccount
import de.kasperczyk.rkbudget.rest.budget.entity.Budget
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import java.math.BigDecimal
import java.time.LocalDate

val testEmailAddress = EmailAddress(fullAddress = "kasperczyk.rene@gmail.com")

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
