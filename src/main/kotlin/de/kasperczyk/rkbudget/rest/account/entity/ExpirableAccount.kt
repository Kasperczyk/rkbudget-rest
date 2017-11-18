package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity

@Entity
abstract class ExpirableAccount(
        name: String,
        profile: Profile,
        @Column var expirationDate: LocalDate,
        accountType: AccountType
) : Account(name = name, profile = profile, accountType = accountType) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ExpirableAccount) return false
        if (!super.equals(other)) return false

        if (expirationDate != other.expirationDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + expirationDate.hashCode()
        return result
    }
}