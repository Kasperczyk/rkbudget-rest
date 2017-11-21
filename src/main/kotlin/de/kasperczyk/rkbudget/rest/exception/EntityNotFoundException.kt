package de.kasperczyk.rkbudget.rest.exception

class EntityNotFoundException(val id: Long, val key: String) : RuntimeException(
        "${key.capitalize().substring(0, key.length - 2)} with id '$id' not found"
)