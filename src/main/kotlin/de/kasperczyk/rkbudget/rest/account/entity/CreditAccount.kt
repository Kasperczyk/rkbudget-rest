package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(CREDIT_ACCOUNT)
class CreditAccount(name: String = "",
                    profile: Profile = Profile(),
                    expirationDate: LocalDate = LocalDate.now(),
                    @Column(name = "ISSUER") var issuer: String = "",
                    @Column(name = "CREDICT_CARD_NUMBER") var creditCardNumber: String = ""
) : ExpirableAccount(name, profile, expirationDate, AccountType.CREDIT) {

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