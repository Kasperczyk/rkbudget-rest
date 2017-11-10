package de.kasperczyk.rkbudget.rest

class ServerError(val errorMessage: String? = null,
                  val parameters: Map<String, String>? = null)