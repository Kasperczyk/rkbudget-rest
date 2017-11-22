package de.kasperczyk.rkbudget.rest.sharer

import de.kasperczyk.rkbudget.rest.profile.ProfileService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SharerServiceTest {

    private lateinit var sharerService: SharerService

    @Mock
    private lateinit var sharerRepositoryMock: SharerRepository

    @Mock
    private lateinit var profileServiceMock: ProfileService

    @Rule
    @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setUp() {
        sharerService = SharerService(sharerRepositoryMock, profileServiceMock)
    }

    @Test
    fun `todo tests`() {

    }
}