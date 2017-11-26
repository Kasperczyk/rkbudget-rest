package de.kasperczyk.rkbudget.rest.fixedTransaction.entity

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "FIXED_INCOME")
class FixedIncome : FixedTransaction() {
}