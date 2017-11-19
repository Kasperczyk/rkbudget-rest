package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(CUSTOM_ACCOUNT)
class CustomAccount(name: String = "",
                    profile: Profile = Profile(),
                    @Column val expirationDate: LocalDate? = null
) : Account(name = name, profile = profile, accountType = AccountType.CUSTOM) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CustomAccount) return false
        if (!super.equals(other)) return false

        if (expirationDate != other.expirationDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (expirationDate?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String =
            "CustomAccount(id=$id, name='$name', profile=$profile, expirationDate=$expirationDate)"
}