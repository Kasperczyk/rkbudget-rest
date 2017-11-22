package de.kasperczyk.rkbudget.rest.fixedTransaction

import de.kasperczyk.rkbudget.rest.profile.ProfileService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FixedTransactionServiceTest {

    private lateinit var fixedTransactionService: FixedTransactionService

    @Mock
    private lateinit var fixedTransactionRepositoryMock: FixedTransactionRepository

    @Mock
    private lateinit var profileServiceMock: ProfileService

    @Rule
    @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setUp() {
        fixedTransactionService = FixedTransactionService(fixedTransactionRepositoryMock, profileServiceMock)
    }

    @Test
    fun `todo tests`() {

    }
}