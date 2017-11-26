package de.kasperczyk.rkbudget.rest.variableTransaction

import de.kasperczyk.rkbudget.rest.AbstractDataJpaTest
import de.kasperczyk.rkbudget.rest.fixedTransaction.FixedTransactionRepository
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class FixedTransactionRepositoryDataJpaTest : AbstractDataJpaTest() {

    @Autowired
    private lateinit var fixedTransactionRepository: FixedTransactionRepository

    @Test
    fun `todo tests`() {

    }
}