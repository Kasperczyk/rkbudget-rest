package de.kasperczyk.rkbudget.rest.profile

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/profiles")
class ProfileRestController(val profileService: ProfileService) {

    @GetMapping("/{profileId}")
    fun getProfileById(@PathVariable profileId: Long): Profile = profileService.getProfileById(profileId)
}