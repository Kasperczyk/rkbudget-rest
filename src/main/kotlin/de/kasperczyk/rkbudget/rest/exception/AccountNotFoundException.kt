package de.kasperczyk.rkbudget.rest.exception

class AccountNotFoundException(val accountId: Long) : RuntimeException(
        "Account with id '$accountId' not found"
)