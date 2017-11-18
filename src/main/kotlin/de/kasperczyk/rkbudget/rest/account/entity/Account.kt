package de.kasperczyk.rkbudget.rest.account.entity

import com.fasterxml.jackson.annotation.JsonTypeInfo
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
abstract class Account(

        @Id
        @GeneratedValue
        val id: Long = 0,

        @Column
        var name: String = "",

        @ManyToOne
        @JoinColumn(name = "profile_id")
        var profile: Profile = Profile(),

        val accountType: AccountType
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Account) return false

        if (name != other.name) return false
        if (profile != other.profile) return false
        if (accountType != other.accountType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + profile.hashCode()
        result = 31 * result + accountType.hashCode()
        return result
    }
}