package de.kasperczyk.rkbudget.rest.profile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository : JpaRepository<Profile, Long> {

    fun findByEmailAddress(emailAddress: EmailAddress): Profile?
}