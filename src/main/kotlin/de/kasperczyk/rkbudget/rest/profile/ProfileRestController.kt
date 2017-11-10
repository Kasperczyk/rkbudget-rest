package de.kasperczyk.rkbudget.rest.profile

import de.kasperczyk.rkbudget.rest.ServerError
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.profile.exception.DuplicateEmailAddressException
import de.kasperczyk.rkbudget.rest.profile.exception.ProfileNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profiles")
class ProfileRestController(val profileService: ProfileService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProfile(@RequestBody profile: Profile): Profile = profileService.createProfile(profile)

    @ExceptionHandler(DuplicateEmailAddressException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleDuplicateEmailAddressException(duplicateEmailAddressException: DuplicateEmailAddressException): ServerError =
            ServerError(errorMessage = duplicateEmailAddressException.message)

    @GetMapping("/{profileEmailAddress:.+}")
    fun getProfileByEmailAddress(@PathVariable profileEmailAddress: EmailAddress): Profile =
            profileService.getProfileByEmailAddress(profileEmailAddress)

    @PutMapping("/{profileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateProfile(@PathVariable profileId: Long, @RequestBody updatedProfile: Profile) {
        profileService.updateProfile(profileId, updatedProfile)
    }

    @DeleteMapping("/{profileId}")
    fun deleteProfile(@PathVariable profileId: Long) {
        profileService.deleteProfile(profileId)
    }

    @ExceptionHandler(ProfileNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleProfileNotFoundException(profileNotFoundException: ProfileNotFoundException): ServerError =
            with(profileNotFoundException) {
                val parameters: Map<String, String> = if (profileNotFoundException.profileEmailAddress != null) {
                    mapOf(pair = "profileEmailAddress" to (profileEmailAddress?.fullAddress ?: ""))
                } else {
                    mapOf(pair = "profileId" to profileId.toString())
                }
                ServerError(errorMessage = message, parameters = parameters)
            }
}