package de.kasperczyk.rkbudget.rest

import java.time.LocalDateTime

class ServerError(val errorMessage: String? = null,
                  val pathParameters: Map<String, String>? = null,
                  val requestParameters: Map<String, String>? = null) {

    val timestamp: LocalDateTime = LocalDateTime.now()
}