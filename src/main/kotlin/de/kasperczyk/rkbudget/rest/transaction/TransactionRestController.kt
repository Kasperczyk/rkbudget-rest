package de.kasperczyk.rkbudget.rest.transaction

import de.kasperczyk.rkbudget.rest.AbstractRestController
import de.kasperczyk.rkbudget.rest.transaction.entity.Transaction
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profiles/{profileId}/transactions")
class TransactionRestController(val transactionService: TransactionService) : AbstractRestController(Transaction::class) {
}