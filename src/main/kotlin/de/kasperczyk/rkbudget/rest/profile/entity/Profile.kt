package de.kasperczyk.rkbudget.rest.profile.entity

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Profile(
        @Id @GeneratedValue val id: Long = 0,
        var firstName: String = "",
        var lastName: String = "",
        var emailAddress: EmailAddress = EmailAddress(),
        var password: String = "") {

    val creationDate: LocalDate = LocalDate.now()
    var lastLoginTimestamp: LocalDateTime? = null
}