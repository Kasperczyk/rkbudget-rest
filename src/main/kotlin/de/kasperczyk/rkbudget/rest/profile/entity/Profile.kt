package de.kasperczyk.rkbudget.rest.profile.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@JsonIgnoreProperties(ignoreUnknown = true) // todo why do I need this?
data class Profile(

        @Id
        @GeneratedValue
        val id: Long = 0,

        @Column
        var firstName: String = "",

        @Column
        var lastName: String = "",

        @Column(unique = true)
        var emailAddress: EmailAddress = EmailAddress(),

        @Column
        var password: String = "",

        @Column
        val creationDate: LocalDate = LocalDate.now(),

        @Column
        var lastLoginTimestamp: LocalDateTime? = null,

        @Column
        var isSharingProfile: Boolean = false
)