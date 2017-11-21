package de.kasperczyk.rkbudget.rest

import de.kasperczyk.rkbudget.rest.exception.EntityNotFoundException
import de.kasperczyk.rkbudget.rest.exception.IdsDoNotMatchException
import de.kasperczyk.rkbudget.rest.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.exception.ServerError
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.reflect.KClass

abstract class AbstractRestController(private val resourceType: KClass<*>) {

    fun validateIds(pathProfileId: Long,
                    pathEntityId: Long,
                    entityProfileId: Long,
                    entityId: Long,
                    key: String) {
        when {
            pathProfileId != entityProfileId ->
                throw IdsDoNotMatchException(pathProfileId, "profileId", entityProfileId)
            pathEntityId != entityId ->
                throw IdsDoNotMatchException(pathEntityId, "$key", entityId)
            else -> return
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequest(httpMessageNotReadableException: HttpMessageNotReadableException): ServerError =
            ServerError("Required request body of type ${resourceType} is missing")

    @ExceptionHandler(ProfileNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    open fun handleProfileNotFoundException(profileNotFoundException: ProfileNotFoundException): ServerError =
            with(profileNotFoundException) {
                ServerError(
                        errorMessage = message,
                        pathVariables = mapOf(pair = "profileId" to profileId.toString())
                )
            }

    @ExceptionHandler(IdsDoNotMatchException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIdsDoNotMatchException(idsDoNotMatchException: IdsDoNotMatchException): ServerError =
            with(idsDoNotMatchException) {
                ServerError(
                        errorMessage = message,
                        pathVariables = mapOf(key to "$pathId")
                )
            }

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEntityNotFoundException(entityNotFoundException: EntityNotFoundException) =
            with(entityNotFoundException) {
                ServerError(
                        errorMessage = message,
                        pathVariables = mapOf(key to "$id")
                )
            }
}