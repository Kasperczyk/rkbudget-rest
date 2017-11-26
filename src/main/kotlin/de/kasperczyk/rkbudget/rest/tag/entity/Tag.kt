package de.kasperczyk.rkbudget.rest.tag.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import javax.persistence.*

@Entity
@Table(name = "TAG")
data class Tag(

        @Id
        @GeneratedValue
        @Column(name = "TAG_ID")
        val id: Long = 0,

        @Column(name = "NAME")
        val name: String = "",

        @ManyToOne
        @JoinColumn(name = "PROFILE_ID")
        var profile: Profile = Profile()
)