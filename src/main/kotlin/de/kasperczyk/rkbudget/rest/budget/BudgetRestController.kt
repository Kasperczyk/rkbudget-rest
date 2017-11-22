package de.kasperczyk.rkbudget.rest.budget

import de.kasperczyk.rkbudget.rest.AbstractRestController
import de.kasperczyk.rkbudget.rest.budget.entity.Budget
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profiles/{profileId]/budgets")
class BudgetRestController(val budgetService: BudgetService) : AbstractRestController(Budget::class) {


}