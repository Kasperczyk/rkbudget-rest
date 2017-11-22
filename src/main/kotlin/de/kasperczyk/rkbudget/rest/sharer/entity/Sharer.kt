package de.kasperczyk.rkbudget.rest.sharer.entity

import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Sharer(

        @Id
        @GeneratedValue
        val id: Long = 0,

        @Column
        var firstName: String = "",

        @Column
        var lastName: String = "",

        @Column
        var emailAddress: EmailAddress = EmailAddress()
)