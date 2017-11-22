package de.kasperczyk.rkbudget.rest.tag

import de.kasperczyk.rkbudget.rest.AbstractService
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import org.springframework.stereotype.Service

@Service
class TagService(val tagRepository: TagRepository,
                 profileService: ProfileService) : AbstractService(profileService) {

    fun createTag(profileId: Long, tag: Tag): Tag {
        val profile = profileService.getProfileById(profileId)
        tag.profile = profile
        return tagRepository.save(tag)
    }

    fun getAllTagsForProfile(profileId: Long): Set<Tag> {
        validateProfile(profileId)
        return tagRepository.findAllByProfileId(profileId)
    }

    fun updateTag(profileId: Long, updatedTag: Tag) {

    }

    fun deleteTag(profileId: Long, tagId: Long) {

    }
}