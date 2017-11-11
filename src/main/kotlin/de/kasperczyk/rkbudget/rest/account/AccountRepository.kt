package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.account.entity.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : CrudRepository<Account, Long>