package de.kasperczyk.rkbudget.rest.profile

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Profile(
        @Id @GeneratedValue val id: Long = 0,
        var firstName: String = "",
        var lastName: String = "",
        var emailAddress: EmailAddress = EmailAddress(fullAddress = ""),
        var password: String = ""
)