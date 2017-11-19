package de.kasperczyk.rkbudget.rest

import java.time.LocalDateTime

class ServerError(val errorMessage: String?,
                  val pathVariables: Map<String, String>? = null,
                  val requestParameters: Map<String, String>? = null,
                  val requestBody: String? = null) {

    val timestamp: LocalDateTime = LocalDateTime.now()
}