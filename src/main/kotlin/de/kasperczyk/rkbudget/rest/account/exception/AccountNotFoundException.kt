package de.kasperczyk.rkbudget.rest.account.exception

class AccountNotFoundException(val accountId: Long) : RuntimeException(
        "Account with id '$accountId' not found"
)