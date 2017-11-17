package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import java.time.LocalDate
import javax.persistence.Entity

@Entity
class GiroAccount(name: String,
                  profile: Profile,
                  expirationDate: LocalDate,
                  institute: String,
                  iban: String
) : BankAccount(name, profile, expirationDate, institute, iban) {

    override fun toString(): String =
            "GiroAccount(id=$id, name='$name', profile=$profile, expirationDate=$expirationDate" +
                    " institute='$institute', iban='$iban')"
}