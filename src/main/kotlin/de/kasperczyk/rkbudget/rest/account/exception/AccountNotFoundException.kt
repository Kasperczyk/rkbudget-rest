package de.kasperczyk.rkbudget.rest.account.exception

class AccountNotFoundException(accountId: Long) : RuntimeException(
        "Account with id '$accountId' not found"
)