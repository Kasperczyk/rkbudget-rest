package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.ServerError
import de.kasperczyk.rkbudget.rest.account.entity.Account
import de.kasperczyk.rkbudget.rest.account.exception.AccountNotFoundException
import de.kasperczyk.rkbudget.rest.exception.IdsDoNotMatchException
import de.kasperczyk.rkbudget.rest.profile.exception.ProfileNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profiles/{profileId}/accounts")
class AccountRestController(val accountService: AccountService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@PathVariable profileId: Long, @RequestBody account: Account) =
            accountService.createAccount(profileId, account)

    @GetMapping
    fun getAllAccountsForProfile(@PathVariable profileId: Long) =
            accountService.getAllAccountsForProfile(profileId)

    @PutMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateAccountForProfile(@PathVariable profileId: Long,
                                @PathVariable accountId: Long,
                                @RequestBody updatedAccount: Account) =
            when {
                profileId != updatedAccount.profile.id ->
                    throw IdsDoNotMatchException(profileId, "profileId", updatedAccount.profile.id)
                accountId != updatedAccount.id ->
                    throw IdsDoNotMatchException(accountId, "accountId", updatedAccount.id)
                else ->
                    accountService.updateAccount(accountId, updatedAccount)
            }

    @DeleteMapping("/{accountId}")
    fun deleteAccountForProfile(@PathVariable profileId: Long,
                                @PathVariable accountId: Long) =
            accountService.deleteAccount(profileId, accountId)

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(httpMessageNotReadableException: HttpMessageNotReadableException): ServerError =
            ServerError("Required request body of type ${Account::class} is missing")

    @ExceptionHandler(ProfileNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleProfileNotFoundException(profileNotFoundException: ProfileNotFoundException): ServerError =
            with(profileNotFoundException) {
                ServerError(
                        errorMessage = message,
                        pathVariables = mapOf(pair = "profileId" to profileId.toString())
                )
            }

    @ExceptionHandler(AccountNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleAccountNotFoundException(accountNotFoundException: AccountNotFoundException) =
            with(accountNotFoundException) {
                ServerError(
                        errorMessage = message,
                        pathVariables = mapOf("accountId" to "$accountId")
                )
            }

    @ExceptionHandler(IdsDoNotMatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIdsDoNotMatchException(idsDoNotMatchException: IdsDoNotMatchException) =
            with(idsDoNotMatchException) {
                ServerError(
                        errorMessage = message,
                        pathVariables = mapOf(key to "$pathId")
                )
            }
}