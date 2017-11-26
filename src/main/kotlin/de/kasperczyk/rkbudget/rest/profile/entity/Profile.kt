package de.kasperczyk.rkbudget.rest.profile.entity

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "PROFILE")
data class Profile(

        @Id
        @GeneratedValue
        @Column(name = "PROFILE_ID")
        val id: Long = 0,

        @Version
        @Column(name = "VERSION")
        private val version: Long = 0,

        @Column(name = "FIRST_NAME")
        var firstName: String = "",

        @Column(name = "LAST_NAME")
        var lastName: String = "",

        @Column(name = "EMAIL_ADDRESS", unique = true)
        var emailAddress: EmailAddress = EmailAddress(),

        @Column(name = "PASSWORD")
        var password: String = "",

        @Column(name = "DATE_CREATED")
        val creationDate: LocalDate = LocalDate.now(),

        @Column(name = "LAST_LOGIN_TIMESTAMP")
        var lastLoginTimestamp: LocalDateTime? = null
)