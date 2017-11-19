package de.kasperczyk.rkbudget.rest.profile.exception

import de.kasperczyk.rkbudget.rest.profile.entity.Profile

class DuplicateEmailAddressException(val profile: Profile) : RuntimeException(
        "A profile with email address '${profile.emailAddress.fullAddress}' has already been registered"
)