package de.kasperczyk.rkbudget.rest

import de.kasperczyk.rkbudget.rest.profile.ProfileRepository
import de.kasperczyk.rkbudget.rest.profile.entity.EmailAddress
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class TestDataInitializer(val profileRepository: ProfileRepository) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val profiles = listOf(
                Profile(firstName = "Rene", lastName = "Kasperczyk", password = "rene123",
                        emailAddress = EmailAddress(fullAddress = "kasperczyk.rene@gmail.com")),
                Profile(firstName = "Christina", lastName = "Kasperczyk", password = "chris123",
                        emailAddress = EmailAddress(fullAddress = "christina.kasperczyk@web.de")))
        profileRepository.save(profiles)
    }
}