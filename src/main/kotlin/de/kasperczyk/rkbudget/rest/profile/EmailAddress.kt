package de.kasperczyk.rkbudget.rest.profile

import javax.persistence.Embeddable

@Embeddable
data class EmailAddress(val fullAddress: String = "")