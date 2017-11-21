package de.kasperczyk.rkbudget.rest.transaction

import de.kasperczyk.rkbudget.rest.transaction.entity.Transaction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : CrudRepository<Transaction, Long>