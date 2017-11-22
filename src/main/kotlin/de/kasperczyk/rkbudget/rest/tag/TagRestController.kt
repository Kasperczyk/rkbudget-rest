package de.kasperczyk.rkbudget.rest.tag

import de.kasperczyk.rkbudget.rest.AbstractRestController
import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("profiles/{profileId}/tags")
class TagRestController(val tagService: TagService) : AbstractRestController(Tag::class) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTag(@PathVariable profileId: Long, @RequestBody tag: Tag): Tag = tagService.createTag(profileId, tag)

    @GetMapping
    fun getAllTags(@PathVariable profileId: Long): Set<Tag> = tagService.getAllTagsForProfile(profileId)

    @PutMapping("/{tagId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateTag(@PathVariable profileId: Long, @PathVariable tagId: Long, @RequestBody updatedTag: Tag) {
        validateIds(profileId, tagId, updatedTag.profile.id, updatedTag.id, "tagId")
        tagService.updateTag(profileId, updatedTag)
    }

    @DeleteMapping("/{tagId}")
    fun deleteTag(@PathVariable profileId: Long, @PathVariable tagId: Long) =
            tagService.deleteTag(profileId, tagId)
}