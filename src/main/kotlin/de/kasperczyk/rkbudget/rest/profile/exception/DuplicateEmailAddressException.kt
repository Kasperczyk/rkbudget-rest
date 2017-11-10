package de.kasperczyk.rkbudget.rest.profile.exception

import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress

class DuplicateEmailAddressException(emailAddress: EmailAddress) : RuntimeException(
        "A profile with email address '${emailAddress.fullAddress}' has already been registered"
)