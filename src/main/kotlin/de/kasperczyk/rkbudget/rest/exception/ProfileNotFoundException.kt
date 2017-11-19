package de.kasperczyk.rkbudget.rest.exception

import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress

class ProfileNotFoundException(val profileEmailAddress: EmailAddress? = null,
                               val profileId: Long? = null) : RuntimeException(
        if (profileEmailAddress != null && profileId == null) {
            "Profile with email address '${profileEmailAddress.fullAddress}' not found"
        } else if (profileId != null && profileEmailAddress == null) {
            "Profile with id '$profileId' not found"
        } else {
            throw IllegalStateException("Either 'profileEmailAddress' or 'profileId' must not be null")
        }
)