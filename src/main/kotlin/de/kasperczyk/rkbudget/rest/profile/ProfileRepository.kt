package de.kasperczyk.rkbudget.rest.profile

import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository : CrudRepository<Profile, Long> {

    fun findByEmailAddress(emailAddress: EmailAddress): Profile?

    fun findById(id: Long): Profile?
}