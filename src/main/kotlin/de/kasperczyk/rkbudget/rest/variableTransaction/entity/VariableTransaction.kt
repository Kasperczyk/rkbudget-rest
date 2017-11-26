package de.kasperczyk.rkbudget.rest.variableTransaction.entity

import de.kasperczyk.rkbudget.rest.EURO
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.sharer.entity.Sharer
import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import org.javamoney.moneta.Money
import java.time.LocalDate
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class VariableTransaction(

        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        val id: Long = 0,

        @Version
        @Column(name = "VERSION")
        private val version: Long = 0,

        @Column(name = "DATE")
        val date: LocalDate = LocalDate.now(),

        @Column(name = "AMOUNT")
        val amount: Money = Money.of(0, EURO),

        @Column(name = "TITLE")
        val title: String = "",

        @ManyToMany
        @JoinTable(
                name = "TRANSACTION_TAG",
                joinColumns = arrayOf(JoinColumn(name = "TRANSACTION_ID")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "TAG_ID"))
        )
        val tags: Set<Tag> = emptySet(),

        @ElementCollection
        @CollectionTable(
                name = "SHARED_WITH",
                joinColumns = arrayOf(JoinColumn(name = "TRANSACTION_ID"))
        )
        val sharedWith: Set<Sharer> = emptySet(),

        @ManyToOne
        @JoinColumn(name = "PROFILE_ID")
        val profile: Profile = Profile(),

        @Column(name = "TRANSACTION_TYPE")
        val transactionType: TransactionType
)