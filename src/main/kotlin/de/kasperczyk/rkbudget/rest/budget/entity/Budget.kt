package de.kasperczyk.rkbudget.rest.budget.entity

import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "BUDGET")
data class Budget(

        @Id
        @GeneratedValue
        @Column(name = "BUDGET_ID")
        val id: Long = 0,

        @Version
        @Column(name = "VERSION")
        private val version: Long = 0,

        @Column(name = "NAME")
        val name: String = "",

        @Column(name = "UPPER_LIMIT")
        val limit: BigDecimal = BigDecimal.ZERO,

        @Column(name = "THRESHOLD_PERCENTAGE")
        val thresholdPercentage: Int = 0,

        @Column(name = "SEND_EMAIL")
        val sendEmail: Boolean = false,

        @ElementCollection
        @CollectionTable(
                name = "BUDGET_TAG",
                joinColumns = arrayOf(JoinColumn(name = "BUDGET_ID"))
        )
        val tags: Set<Tag>
)