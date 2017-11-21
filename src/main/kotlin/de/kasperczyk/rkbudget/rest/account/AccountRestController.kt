package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.AbstractRestController
import de.kasperczyk.rkbudget.rest.account.entity.Account
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profiles/{profileId}/accounts")
class AccountRestController(val accountService: AccountService) : AbstractRestController(Account::class) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@PathVariable profileId: Long, @RequestBody account: Account) = accountService.createAccount(profileId, account)

    @GetMapping
    fun getAllAccountsForProfile(@PathVariable profileId: Long) =
            accountService.getAllAccountsForProfile(profileId)

    @PutMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateAccountForProfile(@PathVariable profileId: Long,
                                @PathVariable accountId: Long,
                                @RequestBody updatedAccount: Account) {
        validateIds(profileId, accountId, updatedAccount.profile.id, updatedAccount.id, "accountId")
        accountService.updateAccount(accountId, updatedAccount)
    }

    @DeleteMapping("/{accountId}")
    fun deleteAccountForProfile(@PathVariable profileId: Long,
                                @PathVariable accountId: Long) =
            accountService.deleteAccount(profileId, accountId)
}