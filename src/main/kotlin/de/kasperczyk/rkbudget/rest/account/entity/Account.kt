package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.account.EURO
import org.javamoney.moneta.Money
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Account(
        @Id @GeneratedValue val id: Long = 0,
        var name: String = "",
        val accountType: AccountType = AccountType.CASH,
        var amount: Money = Money.of(0, EURO)
)