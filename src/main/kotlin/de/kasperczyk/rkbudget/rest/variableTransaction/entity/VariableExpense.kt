package de.kasperczyk.rkbudget.rest.variableTransaction.entity

import de.kasperczyk.rkbudget.rest.EURO
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.sharer.entity.Sharer
import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import org.javamoney.moneta.Money
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "VARIABLE_EXPENSE")
class VariableExpense(date: LocalDate = LocalDate.now(),
                      amount: Money = Money.of(0, EURO),
                      title: String = "",
                      tags: Set<Tag> = emptySet(),
                      sharedWith: Set<Sharer> = emptySet(),
                      profile: Profile = Profile()
) : VariableTransaction(
        date = date,
        amount = amount,
        title = title,
        tags = tags,
        sharedWith = sharedWith,
        profile = profile,
        transactionType = TransactionType.EXPENSE
)