package de.kasperczyk.rkbudget.rest.sharer

import de.kasperczyk.rkbudget.rest.AbstractRestController
import de.kasperczyk.rkbudget.rest.sharer.entity.Sharer
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profiles/{profileId}/sharers")
class SharerRestController(val sharerService: SharerService) : AbstractRestController(Sharer::class) {


}