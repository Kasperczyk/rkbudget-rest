package de.kasperczyk.rkbudget.rest.tag

import de.kasperczyk.rkbudget.rest.profile.ProfileService
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TagServiceTest {

    private lateinit var tagService: TagService

    @Mock
    private lateinit var tagRepositoryMock: TagRepository

    @Mock
    private lateinit var profilServiceMock: ProfileService

    @Rule
    @JvmField
    val expectedException: ExpectedException = ExpectedException.none()

    @Before
    fun setUp() {
        tagService = TagService(tagRepositoryMock, profilServiceMock)
    }

    @Test
    fun `tests todo`() {

    }
}