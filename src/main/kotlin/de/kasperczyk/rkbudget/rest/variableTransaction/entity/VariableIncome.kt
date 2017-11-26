package de.kasperczyk.rkbudget.rest.variableTransaction.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "VARIABLE_INCOME")
class VariableIncome : VariableTransaction(transactionType = TransactionType.INCOME) {
}