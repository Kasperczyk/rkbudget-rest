package de.kasperczyk.rkbudget.rest.budget

import de.kasperczyk.rkbudget.rest.AbstractService
import de.kasperczyk.rkbudget.rest.budget.entity.Budget
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import org.springframework.stereotype.Service

@Service
class BudgetService(val budgetRepository: BudgetRepository,
                    profileService: ProfileService) : AbstractService(profileService) {

    fun createBudget(profileId: Long, budget: Budget): Budget = budgetRepository.save(budget)
}