package de.kasperczyk.rkbudget.rest.sharer.entity

import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import javax.persistence.*

@Entity
@Table(name = "SHARER")
data class Sharer(

        @Id
        @GeneratedValue
        @Column(name = "SHARER_ID")
        val id: Long = 0,

        @Version
        @Column(name = "VERSION")
        private val version: Long = 0,

        @Column(name = "FIRST_NAME")
        var firstName: String = "",

        @Column(name = "LAST_NAME")
        var lastName: String = "",

        @Column(name = "EMAIL_ADDRESS")
        var emailAddress: EmailAddress = EmailAddress(),

        @ManyToOne
        @JoinColumn(name = "PROFILE_ID")
        val profile: Profile = Profile()
)