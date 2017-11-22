package de.kasperczyk.rkbudget.rest.tag

import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : CrudRepository<Tag, Long> {

    fun findAllByProfileId(profileId: Long): Set<Tag>
}