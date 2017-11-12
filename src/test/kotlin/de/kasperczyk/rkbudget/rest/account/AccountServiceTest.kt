package de.kasperczyk.rkbudget.rest.account

import de.kasperczyk.rkbudget.rest.account.entity.Account
import de.kasperczyk.rkbudget.rest.account.entity.AccountType
import de.kasperczyk.rkbudget.rest.account.exception.AccountNotFoundException
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import de.kasperczyk.rkbudget.rest.profile.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.testAccount
import de.kasperczyk.rkbudget.rest.testProfile
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner
import java.time.LocalDate

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
    fun `getting all accounts for a profile loads the associated accounts from the database and returns them`() {
        `when`(profileServiceMock.exists(testProfile.id)).thenReturn(true)
        `when`(accountRepositoryMock.findAllByProfileId(testProfile.id)).thenReturn(listOf(testAccount))
        val accounts = accountService.getAllAccountsForProfile(testProfile.id)
        assertThat(accounts, `is`(listOf(testAccount)))
        verify(accountRepositoryMock).findAllByProfileId(testProfile.id)
    }

    @Test
    fun `trying to load all accounts from a non-existent profile throws a ProfileNotFoundException`() {
        `when`(profileServiceMock.exists(testProfile.id)).thenReturn(false)
        expectedException.expect(ProfileNotFoundException::class.java)
        expectedException.expectMessage("Profile with id '${testProfile.id}' not found")
        accountService.getAllAccountsForProfile(testProfile.id)
    }

    @Test
    fun `updating an existing account updates that account in the database`() {
        val updatedAccount = Account(
                id = 1,
                accountType = AccountType.GIRO,
                name = "Other Giro Account",
                institute = "Other Bank",
                iban = "some iban",
                expirationDate = LocalDate.of(2030, 10, 30),
                profile = testProfile
        )
        assertThat(updatedAccount, `is`(not(testAccount)))
        `when`(profileServiceMock.exists(updatedAccount.profile.id)).thenReturn(true)
        `when`(accountRepositoryMock.exists(updatedAccount.id)).thenReturn(true)
        `when`(accountRepositoryMock.findOne(updatedAccount.id)).thenReturn(testAccount.copy(id = 1))
        accountService.updateAccount(updatedAccount.id, updatedAccount)
        verify(accountRepositoryMock).findOne(updatedAccount.id)
        verify(accountRepositoryMock).save(updatedAccount)
    }

    @Test
    fun `trying to update a non-existent account throws an AccountNotFoundException`() {
        `when`(profileServiceMock.exists(testProfile.id)).thenReturn(true)
        `when`(accountRepositoryMock.exists(-1)).thenReturn(false)
        expectedException.expect(AccountNotFoundException::class.java)
        expectedException.expectMessage("Account with id '-1' not found")
        accountService.updateAccount(-1, testAccount)
        verify(profileServiceMock).exists(testProfile.id)
        verify(accountRepositoryMock).exists(-1)
        verify(accountRepositoryMock, never()).save(any(Account::class.java))
    }

    @Test
    fun `trying to update an account for a non-existent profile throws a ProfileNotFoundException`() {
        `when`(profileServiceMock.exists(testAccount.id)).thenReturn(false)
        expectedException.expect(ProfileNotFoundException::class.java)
        expectedException.expectMessage("Profile with id '${testProfile.id}' not found")
        accountService.updateAccount(testAccount.id, testAccount)
        verify(profileServiceMock).exists(testProfile.id)
        verify(accountRepositoryMock, never()).exists(anyLong())
        verify(accountRepositoryMock, never()).save(any(Account::class.java))
    }

    @Test
    fun `deleting an existing account deletes that account from the database`() {
        `when`(profileServiceMock.exists(testProfile.id)).thenReturn(true)
        `when`(accountRepositoryMock.exists(testAccount.id)).thenReturn(true)
        accountService.deleteAccount(testProfile.id, testAccount.id)
        verify(profileServiceMock).exists(anyLong())
        verify(accountRepositoryMock).exists(anyLong())
        verify(accountRepositoryMock).delete(testAccount.id)
    }

    @Test
    fun `trying to delete a non-existent account throws an AccountNotFoundException`() {
        `when`(profileServiceMock.exists(testProfile.id)).thenReturn(true)
        `when`(accountRepositoryMock.exists(-1)).thenReturn(false)
        expectedException.expect(AccountNotFoundException::class.java)
        expectedException.expectMessage("Account with id '${testAccount.id}' not found")
        accountService.deleteAccount(testProfile.id, testAccount.id)
        verify(profileServiceMock).exists(testProfile.id)
        verify(accountRepositoryMock).exists(-1)
        verify(accountRepositoryMock, never()).delete(any(Account::class.java))
    }

    @Test
    fun `trying to delete an account for a non-existent profile throws a ProfileNotFoundException`() {
        `when`(profileServiceMock.exists(testAccount.id)).thenReturn(false)
        expectedException.expect(ProfileNotFoundException::class.java)
        expectedException.expectMessage("Profile with id '${testProfile.id}' not found")
        accountService.deleteAccount(testProfile.id, testAccount.id)
        verify(profileServiceMock).exists(testProfile.id)
        verify(accountRepositoryMock, never()).exists(anyLong())
        verify(accountRepositoryMock, never()).delete(any(Account::class.java))
    }
}