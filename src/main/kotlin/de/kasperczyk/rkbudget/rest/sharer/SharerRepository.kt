package de.kasperczyk.rkbudget.rest.sharer

import de.kasperczyk.rkbudget.rest.sharer.entity.Sharer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SharerRepository : CrudRepository<Sharer, Long> {

    fun findAllByProfileId(profileId: Long): List<Sharer>
}