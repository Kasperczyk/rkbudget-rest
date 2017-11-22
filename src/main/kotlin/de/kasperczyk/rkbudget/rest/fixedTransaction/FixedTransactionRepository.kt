package de.kasperczyk.rkbudget.rest.fixedTransaction

import de.kasperczyk.rkbudget.rest.fixedTransaction.entity.FixedTransaction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FixedTransactionRepository : CrudRepository<FixedTransaction, Long>