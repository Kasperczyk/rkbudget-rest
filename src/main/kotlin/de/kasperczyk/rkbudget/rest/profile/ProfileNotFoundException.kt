package de.kasperczyk.rkbudget.rest.profile

class ProfileNotFoundException(val profileEmailAddress: EmailAddress) : RuntimeException()