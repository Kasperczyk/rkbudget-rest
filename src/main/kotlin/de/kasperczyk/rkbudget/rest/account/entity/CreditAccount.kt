package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class CreditAccount(name: String,
                    profile: Profile,
                    expirationDate: LocalDate,
                    @Column var issuer: String,
                    @Column var creditCardNumber: String
) : ExpirableAccount(name, profile, expirationDate) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CreditAccount) return false
        if (!super.equals(other)) return false

        if (issuer != other.issuer) return false
        if (creditCardNumber != other.creditCardNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + issuer.hashCode()
        result = 31 * result + creditCardNumber.hashCode()
        return result
    }

    override fun toString(): String =
            "CreditAccount(id=$id, name='$name', profile=$profile, expirationDate=$expirationDate" +
                    " issuer='$issuer', creditCardNumber='$creditCardNumber')"

}