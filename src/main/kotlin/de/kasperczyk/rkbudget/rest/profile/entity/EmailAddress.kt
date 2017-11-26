package de.kasperczyk.rkbudget.rest.profile.entity

import org.hibernate.validator.constraints.Email
import javax.persistence.Embeddable

@Embeddable
data class EmailAddress(@Email val fullAddress: String = "")