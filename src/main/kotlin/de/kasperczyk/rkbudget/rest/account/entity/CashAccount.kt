package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import javax.persistence.Entity

@Entity
class CashAccount(name: String,
                  profile: Profile
) : Account(name = name, profile = profile) {

    override fun toString(): String = "CashAccount(id=$id, name='$name', profile=$profile)"
}