package de.kasperczyk.rkbudget.rest.tag.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import javax.persistence.*

@Entity
data class Tag(

        @Id
        @GeneratedValue
        val id: Long = 0,

        @Column
        val name: String = "",

        @ManyToOne
        @JoinColumn(name = "profile_id")
        val profile: Profile = Profile()
)