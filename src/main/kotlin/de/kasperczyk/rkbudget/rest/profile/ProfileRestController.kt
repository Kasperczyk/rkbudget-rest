package de.kasperczyk.rkbudget.rest.profile

import de.kasperczyk.rkbudget.rest.ServerError
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.profile.exception.DuplicateEmailAddressException
import de.kasperczyk.rkbudget.rest.profile.exception.IdsDoNotMatchException
import de.kasperczyk.rkbudget.rest.profile.exception.ProfileNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profiles")
class ProfileRestController(val profileService: ProfileService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProfile(@RequestBody profile: Profile): Profile = profileService.createProfile(profile)

    @GetMapping("/{profileEmailAddress:.+}")
    fun getProfileByEmailAddress(@PathVariable profileEmailAddress: EmailAddress): Profile =
            profileService.getProfileByEmailAddress(profileEmailAddress)

    @PutMapping("/{profileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateProfile(@PathVariable profileId: Long, @RequestBody updatedProfile: Profile) =
            if (profileId == updatedProfile.id) {
                profileService.updateProfile(updatedProfile)
            } else {
                throw IdsDoNotMatchException(profileId, updatedProfile)
            }

    @DeleteMapping("/{profileId}")
    fun deleteProfile(@PathVariable profileId: Long) = profileService.deleteProfile(profileId)

    @ExceptionHandler(DuplicateEmailAddressException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleDuplicateEmailAddressException(duplicateEmailAddressException: DuplicateEmailAddressException): ServerError =
            ServerError(
                    errorMessage = duplicateEmailAddressException.message,
                    requestParameters = mapOf("emailAddress" to duplicateEmailAddressException.emailAddress.fullAddress)
            )

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(httpMessageNotReadableException: HttpMessageNotReadableException): ServerError =
            ServerError(errorMessage = "Required request body of type ${Profile::class} is missing")

    @ExceptionHandler(ProfileNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleProfileNotFoundException(profileNotFoundException: ProfileNotFoundException): ServerError =
            with(profileNotFoundException) {
                val parameters: Map<String, String> = if (profileNotFoundException.profileEmailAddress != null) {
                    mapOf(pair = "profileEmailAddress" to (profileEmailAddress?.fullAddress ?: ""))
                } else {
                    mapOf(pair = "profileId" to profileId.toString())
                }
                ServerError(errorMessage = message, pathParameters = parameters)
            }

    @ExceptionHandler(IdsDoNotMatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIdsDoNotMatchException(idsDoNotMatchException: IdsDoNotMatchException): ServerError =
            with(idsDoNotMatchException) {
                ServerError(
                        errorMessage = idsDoNotMatchException.message,
                        pathParameters = mapOf("profileId" to "$profileId"),
                        requestParameters = mapOf("profile" to profile.toString())
                )
            }
}