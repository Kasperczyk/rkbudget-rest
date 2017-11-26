package de.kasperczyk.rkbudget.rest.variableTransaction

import de.kasperczyk.rkbudget.rest.variableTransaction.entity.VariableTransaction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : CrudRepository<VariableTransaction, Long>