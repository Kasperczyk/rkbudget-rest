package de.kasperczyk.rkbudget.rest.exception

class IdsDoNotMatchException(val pathId: Long, val key: String, updatedObjectId: Long) : RuntimeException(
        "Ids do not match. The id specified is '$pathId', " +
                "the object in the request body contained id '$updatedObjectId'"
)