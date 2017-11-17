package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import java.time.LocalDate
import javax.persistence.Entity

@Entity
class SavingsAccount(name: String,
                     profile: Profile,
                     expirationDate: LocalDate,
                     institute: String,
                     iban: String
) : BankAccount(name, profile, expirationDate, institute, iban) {

    override fun toString(): String {
        return "SavingsAccount(id=$id, name='$name', profile=$profile, institute='$institute', iban='$iban')"
    }
}