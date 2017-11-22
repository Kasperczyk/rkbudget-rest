package de.kasperczyk.rkbudget.rest.budget

import de.kasperczyk.rkbudget.rest.budget.entity.Budget
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BudgetRepository : CrudRepository<Budget, Long>