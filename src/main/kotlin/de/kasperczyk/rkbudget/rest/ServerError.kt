package de.kasperczyk.rkbudget.rest

class ServerError(val errorMessage: String = "",
                  val parameters: Map<String, String>? = null)