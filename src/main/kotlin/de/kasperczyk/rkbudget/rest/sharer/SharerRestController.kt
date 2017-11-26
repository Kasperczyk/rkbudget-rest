package de.kasperczyk.rkbudget.rest.sharer

import de.kasperczyk.rkbudget.rest.AbstractRestController
import de.kasperczyk.rkbudget.rest.sharer.entity.Sharer
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/profiles/{profileId}/sharers")
class SharerRestController(val sharerService: SharerService) : AbstractRestController(Sharer::class) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSharer(@PathVariable profileId: Long, @RequestBody sharer: Sharer): Sharer =
            sharerService.createSharer(profileId, sharer)

    @GetMapping
    fun getAllSharers(@PathVariable profileId: Long): List<Sharer> = sharerService.getAllSharersForProfile(profileId)
}