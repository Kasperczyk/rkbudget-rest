package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity

@Entity
abstract class BankAccount(
        name: String,
        profile: Profile,
        expirationDate: LocalDate,
        @Column var institute: String,
        @Column var iban: String
) : ExpirableAccount(name, profile, expirationDate) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BankAccount) return false
        if (!super.equals(other)) return false

        if (institute != other.institute) return false
        if (iban != other.iban) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + institute.hashCode()
        result = 31 * result + iban.hashCode()
        return result
    }
}