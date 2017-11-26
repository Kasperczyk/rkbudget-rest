package de.kasperczyk.rkbudget.rest.variableTransaction

import de.kasperczyk.rkbudget.rest.profile.ProfileService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TransactionServiceTest {

    private lateinit var transactionService: TransactionService

    @Mock
    private lateinit var transactionRepositoryMock: TransactionRepository

    @Mock
    private lateinit var profileServiceMock: ProfileService

    @Rule
    @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setUp() {
        transactionService = TransactionService(transactionRepositoryMock, profileServiceMock)
    }

    @Test
    fun `todo tests`() {

    }
}