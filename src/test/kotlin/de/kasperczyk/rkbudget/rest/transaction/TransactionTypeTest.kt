package de.kasperczyk.rkbudget.rest.transaction

import de.kasperczyk.rkbudget.rest.transaction.entity.EXPENSE_TRANSACTION
import de.kasperczyk.rkbudget.rest.transaction.entity.INCOME_TRANSACTION
import de.kasperczyk.rkbudget.rest.transaction.entity.TransactionType
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class TransactionTypeTest {

    @Test
    fun `check that TransactionType constant values are correct and match the enum value's name`() {
        assertThat(EXPENSE_TRANSACTION, `is`("EXPENSE"))
        assertThat(INCOME_TRANSACTION, `is`("INCOME"))

        assertThat(EXPENSE_TRANSACTION, `is`(TransactionType.EXPENSE.name))
        assertThat(INCOME_TRANSACTION, `is`(TransactionType.INCOME.name))
    }
}