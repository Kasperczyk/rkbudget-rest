package de.kasperczyk.rkbudget.rest.profile

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository : CrudRepository<Profile, Long> {

    fun findByEmailAddress(emailAddress: EmailAddress): Profile?
}