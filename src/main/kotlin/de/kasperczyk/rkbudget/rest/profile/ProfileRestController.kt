package de.kasperczyk.rkbudget.rest.profile

import de.kasperczyk.rkbudget.rest.ServerError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profiles")
class ProfileRestController(val profileService: ProfileService) {

    @GetMapping("/{profileEmailAddress:.+}")
    fun getProfileByEmailAddress(@PathVariable profileEmailAddress: EmailAddress): Profile =
            profileService.getProfileByEmailAddress(profileEmailAddress)

    @ExceptionHandler(ProfileNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleProfileNotFoundException(profileNotFoundException: ProfileNotFoundException): ServerError =
            with(profileNotFoundException.profileEmailAddress) {
                ServerError(
                        errorMessage = "Profile with email address '$fullAddress' not found",
                        parameters = mapOf(pair = "profileEmailAddress" to fullAddress)
                )
            }
}