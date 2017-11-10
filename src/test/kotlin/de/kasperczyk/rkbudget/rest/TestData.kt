package de.kasperczyk.rkbudget.rest

import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile

val testEmailAddress = EmailAddress(fullAddress = "kasperczyk.rene@gmail.com")

val testProfile = Profile(
        firstName = "Rene",
        lastName = "Kasperczyk",
        emailAddress = testEmailAddress,
        password = "secret"
)