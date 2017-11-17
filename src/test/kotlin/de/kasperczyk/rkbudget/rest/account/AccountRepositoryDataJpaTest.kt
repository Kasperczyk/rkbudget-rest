package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.AbstractDataJpaTest
import de.kasperczyk.rkbudget.rest.account.entity.CashAccount
import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.testAccount
import de.kasperczyk.rkbudget.rest.testProfile
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class AccountRepositoryDataJpaTest : AbstractDataJpaTest() {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Test
    fun `findAllByProfileId returns all accounts for the given profile`() {
        val profile = Profile(
                firstName = testProfile.firstName,
                lastName = testProfile.lastName,
                emailAddress = testProfile.emailAddress,
                password = testProfile.password
        )
        entityManager.persist(profile)
        val account = CashAccount(
                name = testAccount.name,
                profile = profile
        )
        entityManager.persist(account)
        val accounts = accountRepository.findAllByProfileId(profile.id)
        assertThat(accounts[0] as CashAccount, `is`(account))
        assertThat(accounts.size, `is`(1))
    }
}