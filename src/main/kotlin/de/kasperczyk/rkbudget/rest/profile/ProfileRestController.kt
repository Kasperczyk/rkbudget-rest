package de.kasperczyk.rkbudget.rest.profile

import com.fasterxml.jackson.databind.ObjectMapper
import de.kasperczyk.rkbudget.rest.AbstractRestController
import de.kasperczyk.rkbudget.rest.exception.DuplicateEmailAddressException
import de.kasperczyk.rkbudget.rest.exception.IdsDoNotMatchException
import de.kasperczyk.rkbudget.rest.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.exception.ServerError
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profiles")
class ProfileRestController(val profileService: ProfileService, val objectMapper: ObjectMapper) : AbstractRestController(Profile::class) {

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
                throw IdsDoNotMatchException(profileId, "profileId", updatedProfile.id)
            }

    @DeleteMapping("/{profileId}")
    fun deleteProfile(@PathVariable profileId: Long) = profileService.deleteProfile(profileId)

    @ExceptionHandler(DuplicateEmailAddressException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleDuplicateEmailAddressException(duplicateEmailAddressException: DuplicateEmailAddressException): ServerError =
            ServerError(
                    errorMessage = duplicateEmailAddressException.message,
                    requestBody = objectMapper.writeValueAsString(duplicateEmailAddressException.profile)
            )

    @ExceptionHandler(ProfileNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    override fun handleProfileNotFoundException(profileNotFoundException: ProfileNotFoundException): ServerError =
            with(profileNotFoundException) {
                val parameters: Map<String, String> = if (profileNotFoundException.profileEmailAddress != null) {
                    mapOf(pair = "profileEmailAddress" to (profileEmailAddress?.fullAddress ?: ""))
                } else {
                    mapOf(pair = "profileId" to profileId.toString())
                }
                ServerError(
                        errorMessage = message,
                        pathVariables = parameters
                )
            }
}