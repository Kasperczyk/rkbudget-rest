package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import java.time.LocalDate
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(GIRO_ACCOUNT)
class GiroAccount(name: String = "",
                  profile: Profile = Profile(),
                  expirationDate: LocalDate = LocalDate.now(),
                  institute: String = "",
                  iban: String = ""
) : BankAccount(name, profile, expirationDate, institute, iban, AccountType.GIRO) {

    override fun toString(): String =
            "GiroAccount(id=$id, name='$name', profile=$profile, expirationDate=$expirationDate" +
                    " institute='$institute', iban='$iban')"
}