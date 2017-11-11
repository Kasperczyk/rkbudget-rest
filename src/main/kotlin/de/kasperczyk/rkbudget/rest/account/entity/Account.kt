package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import java.time.LocalDate
import javax.persistence.*

@Entity
data class Account(

        @Id
        @GeneratedValue
        val id: Long = 0,

        @Column
        var name: String = "",

        @Column
        val accountType: AccountType = AccountType.CUSTOM,

        @Column
        var institute: String? = null,

        @Column(unique = true)
        var iban: String? = null,

        @Column(unique = true)
        var creditCardNumber: String? = null,

        @Column
        var expirationDate: LocalDate? = null,

        @ManyToOne
        @JoinColumn(name = "profile_id")
        var profile: Profile = Profile()
)