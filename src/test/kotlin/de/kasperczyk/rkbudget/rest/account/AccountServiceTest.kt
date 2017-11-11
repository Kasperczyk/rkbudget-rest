package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.profile.ProfileService
import de.kasperczyk.rkbudget.rest.profile.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.testAccount
import de.kasperczyk.rkbudget.rest.testProfile
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AccountServiceTest {

    private lateinit var accountService: AccountService

    @Mock
    private lateinit var accountRepositoryMock: AccountRepository

    @Mock
    private lateinit var profileServiceMock: ProfileService

    @Rule
    @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setUp() {
        accountService = AccountService(accountRepositoryMock, profileServiceMock)
    }

    @Test
    fun `creating an account saves is to the database and returns it`() {
        `when`(profileServiceMock.getProfileById(testProfile.id)).thenReturn(testProfile)
        `when`(accountRepositoryMock.save(testAccount)).thenReturn(testAccount)
        val account = accountService.createAccount(testProfile.id, testAccount)
        verify(profileServiceMock).getProfileById(testProfile.id)
        verify(accountRepositoryMock).save(testAccount)
        assertThat(account, `is`(testAccount))
        assertThat(account.profile, `is`(testProfile))
    }

    @Test
    fun `trying to create an account for a non-existent profile throws a ProfileNotFoundException`() {
        `when`(profileServiceMock.getProfileById(testProfile.id))
                .thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        expectedException.expect(ProfileNotFoundException::class.java)
        expectedException.expectMessage("Profile with id '${testProfile.id}' not found")
        accountService.createAccount(testProfile.id, testAccount)
        verify(profileServiceMock).getProfileById(testProfile.id)
        verify(accountRepositoryMock, never()).save(testAccount)
    }

    @Test
    fun `getting an account loads the account from the database and returns it`() {

    }

    @Test
    fun `trying to get a non-existent account throws an AccountNotFoundException`() {

    }

    @Test
    fun `updating an existing account updates that account in the database`() {

    }

    @Test
    fun `trying to update a non-existent account throws an AccountNotFoundException`() {

    }

    @Test
    fun `deleting an existing account deletes that account from the database`() {

    }

    @Test
    fun `trying to delete a non-existent account throws an AccountNotFoundException`() {

    }
}