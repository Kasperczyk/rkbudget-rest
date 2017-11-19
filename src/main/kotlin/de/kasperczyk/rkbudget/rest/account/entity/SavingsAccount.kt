package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import java.time.LocalDate
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue(SAVINGS_ACCOUNT)
class SavingsAccount(name: String = "",
                     profile: Profile = Profile(),
                     expirationDate: LocalDate = LocalDate.now(),
                     institute: String = "",
                     iban: String = ""
) : BankAccount(name, profile, expirationDate, institute, iban, AccountType.SAVINGS) {

    override fun toString(): String {
        return "SavingsAccount(id=$id, name='$name', profile=$profile, institute='$institute', iban='$iban')"
    }
}