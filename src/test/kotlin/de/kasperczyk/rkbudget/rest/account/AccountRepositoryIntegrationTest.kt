package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.AbstractTransactionalIntegrationTest
import de.kasperczyk.rkbudget.rest.testAccount
import de.kasperczyk.rkbudget.rest.testProfile
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class AccountRepositoryIntegrationTest : AbstractTransactionalIntegrationTest() {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Test
    fun `findAllByProfileId returns all accounts for the given profile`() {
        val accounts = accountRepository.findAllByProfileId(testProfile.id)
        assertThat(accounts[0], `is`(testAccount))
        assertThat(accounts.size, `is`(5))
    }
}