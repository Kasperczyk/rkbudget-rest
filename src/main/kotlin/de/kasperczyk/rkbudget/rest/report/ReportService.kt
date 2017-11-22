package de.kasperczyk.rkbudget.rest.report

import de.kasperczyk.rkbudget.rest.AbstractService
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import org.springframework.stereotype.Service

@Service
class ReportService(profileService: ProfileService) : AbstractService(profileService) {


}