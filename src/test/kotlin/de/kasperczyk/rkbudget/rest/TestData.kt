package de.kasperczyk.rkbudget.rest

import de.kasperczyk.rkbudget.rest.account.entity.Account
import de.kasperczyk.rkbudget.rest.account.entity.AccountType
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import java.time.LocalDate

val testEmailAddress = EmailAddress(fullAddress = "kasperczyk.rene@gmail.com")

val testProfile = Profile(
        firstName = "Rene",
        lastName = "Kasperczyk",
        emailAddress = testEmailAddress,
        password = "secret"
)

val testAccount = Account(
        name = "Rene's Giro Account",
        accountType = AccountType.GIRO,
        institute = "Some Bank",
        iban = "DE12 3456 7890 1234 5678 90",
        expirationDate = LocalDate.of(2020, 12, 31),
        profile = testProfile
)
