package de.kasperczyk.rkbudget.rest.budget.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Budget(

        @Id
        @GeneratedValue
        val id: Long = 0
)