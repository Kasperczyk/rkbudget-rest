package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(CASH_ACCOUNT)
class CashAccount(name: String = "",
                  profile: Profile = Profile()
) : Account(name = name, profile = profile, accountType = AccountType.CASH) {

    override fun toString(): String = "CashAccount(id=$id, name='$name', profile=$profile)"
}