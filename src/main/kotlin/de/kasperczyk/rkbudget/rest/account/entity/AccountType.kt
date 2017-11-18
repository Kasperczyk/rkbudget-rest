package de.kasperczyk.rkbudget.rest.account.entity

const val CASH_ACCOUNT = "CASH"
const val GIRO_ACCOUNT = "GIRO"
const val SAVINGS_ACCOUNT = "SAVINGS"
const val CREDIT_ACCOUNT = "CREDIT"
const val CUSTOM_ACCOUNT = "CUSTOM"

enum class AccountType {

    CASH,
    GIRO,
    SAVINGS,
    CREDIT,
    CUSTOM;
}