package de.kasperczyk.rkbudget.rest.transaction.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.sharer.entity.Sharer
import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import org.javamoney.moneta.Money
import java.time.LocalDate
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class Transaction(

        @Id
        @GeneratedValue
        val id: Long = 0,

        @Column
        val date: LocalDate,

        @Column
        val amount: Money,

        @Column
        val title: String = "",

        @ManyToMany
        @JoinTable(
                name = "transaction_tag",
                joinColumns = arrayOf(JoinColumn(name = "transaction_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "tag_id"))
        )
        val tags: Set<Tag> = emptySet(),

        @ElementCollection
        @CollectionTable(
                name = "shared_with",
                joinColumns = arrayOf(JoinColumn(name = "transaction_id"))
        )
        val sharedWith: Set<Sharer> = emptySet(),

        @ManyToOne
        @JoinColumn(name = "profile_id")
        val profile: Profile,

        @Column
        val transactionType: TransactionType
)