package de.kasperczyk.rkbudget.rest.account.exception

import de.kasperczyk.rkbudget.rest.account.entity.Account

class DuplicateAccountException(account: Account) : RuntimeException(
        "The account already exists: $account"
)