package de.kasperczyk.rkbudget.rest.fixedTransaction.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
abstract class FixedTransaction(

        @Id
        @GeneratedValue
        val id: Long = 0
)