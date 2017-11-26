package de.kasperczyk.rkbudget.rest.variableTransaction

import de.kasperczyk.rkbudget.rest.AbstractRestController
import de.kasperczyk.rkbudget.rest.variableTransaction.entity.VariableTransaction
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profiles/{profileId}/transactions")
class TransactionRestController(val transactionService: TransactionService) : AbstractRestController(VariableTransaction::class) {
}