package de.kasperczyk.rkbudget.rest.fixedTransaction.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "FIXED_EXPENSE")
class FixedExpense : FixedTransaction() {
}