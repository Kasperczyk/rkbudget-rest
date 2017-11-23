package de.kasperczyk.rkbudget.rest.budget.entity

import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import java.math.BigDecimal
import javax.persistence.*

@Entity
data class Budget(

        @Id
        @GeneratedValue
        val id: Long = 0,

        @Version
        private val version: Long = 0,

        @Column
        val name: String = "",

        @Column
        val limit: BigDecimal = BigDecimal.ZERO,

        @Column
        val thresholdPercentage: Int = 0,

        @Column
        val sendEmail: Boolean = false,

        @ElementCollection
        @CollectionTable(
                name = "budget_tag",
                joinColumns = arrayOf(JoinColumn(name = "budget_id"))
        )
        val tags: Set<Tag>
)