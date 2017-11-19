package de.kasperczyk.rkbudget.rest.account

import com.fasterxml.jackson.databind.ObjectMapper
import de.kasperczyk.rkbudget.rest.ServerError
import de.kasperczyk.rkbudget.rest.account.entity.Account
import de.kasperczyk.rkbudget.rest.profile.exception.ProfileNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profiles/{profileId}/accounts")
class AccountRestController(val accountService: AccountService, val objectMapper: ObjectMapper) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createAccount(@PathVariable profileId: Long, @RequestBody account: Account) =
            accountService.createAccount(profileId, account)

    @ExceptionHandler(ProfileNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleProfileNotFoundException(profileNotFoundException: ProfileNotFoundException): ServerError =
            with(profileNotFoundException) {
                ServerError(
                        errorMessage = message,
                        pathVariables = mapOf(pair = "profileId" to profileId.toString())
                )
            }

    @GetMapping
    fun get(@PathVariable profileId: Long) =
            accountService.getAllAccountsForProfile(profileId)
}