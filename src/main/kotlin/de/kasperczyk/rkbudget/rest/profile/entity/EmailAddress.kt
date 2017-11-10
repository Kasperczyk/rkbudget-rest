package de.kasperczyk.rkbudget.rest.profile.entity

import javax.persistence.Embeddable

@Embeddable
data class EmailAddress(val fullAddress: String = "")