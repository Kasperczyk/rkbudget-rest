package de.kasperczyk.rkbudget.rest.profile.exception

import de.kasperczyk.rkbudget.rest.profile.entity.Profile

class IdsDoNotMatchException(val profileId: Long, val profile: Profile) : RuntimeException(
        "Ids do not match. The path specified id '$profileId', " +
                "the object in the request body contained id '${profile.id}'"
)