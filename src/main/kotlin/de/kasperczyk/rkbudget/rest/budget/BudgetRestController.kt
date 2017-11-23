package de.kasperczyk.rkbudget.rest.budget

import de.kasperczyk.rkbudget.rest.AbstractRestController
import de.kasperczyk.rkbudget.rest.budget.entity.Budget
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profiles/{profileId}/budgets")
class BudgetRestController(val budgetService: BudgetService) : AbstractRestController(Budget::class) {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBudget(@PathVariable profileId: Long, @RequestBody budget: Budget) = budgetService.createBudget(profileId, budget)
}