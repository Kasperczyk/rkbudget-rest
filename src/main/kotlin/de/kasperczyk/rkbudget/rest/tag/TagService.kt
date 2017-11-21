package de.kasperczyk.rkbudget.rest.tag

import de.kasperczyk.rkbudget.rest.profile.ProfileService
import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import org.springframework.stereotype.Service

@Service
class TagService(val tagRepository: TagRepository,
                 val profileService: ProfileService) {

    fun createTag(profileId: Long, tag: Tag): Tag = tagRepository.save(tag)

    fun getAllTagsForProfile(profileId: Long): List<Tag> =
            listOf(Tag())

    fun updateTag(profileId: Long, updatedTag: Tag) {

    }

    fun deleteTag(profileId: Long, tagId: Long) {

    }
}