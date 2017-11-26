package de.kasperczyk.rkbudget.rest.fixedTransaction.entity

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class FixedTransaction(

        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        val id: Long = 0,

        @Version
        @Column(name = "VERSION")
        private val version: Long = 0
)