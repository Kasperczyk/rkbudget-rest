package de.kasperczyk.rkbudget.rest.tag

import de.kasperczyk.rkbudget.rest.exception.ProfileNotFoundException
import de.kasperczyk.rkbudget.rest.profile.ProfileService
import de.kasperczyk.rkbudget.rest.tag.entity.Tag
import de.kasperczyk.rkbudget.rest.testProfile
import de.kasperczyk.rkbudget.rest.testTag
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
    fun `creating a tag saves it to the database and returns it`() {
        `when`(profilServiceMock.getProfileById(testProfile.id)).thenReturn(testProfile)
        `when`(tagRepositoryMock.save(testTag)).thenReturn(testTag)
        val result = tagService.createTag(testProfile.id, testTag)
        assertThat(result, `is`(testTag))
        assertThat(result.profile, `is`(testProfile))
        verify(profilServiceMock).getProfileById(testProfile.id)
        verify(tagRepositoryMock).save(testTag)
    }

    @Test
    fun `trying to create a tag for a non-existent profile throws a ProfileNotFoundException`() {
        `when`(profilServiceMock.getProfileById(testProfile.id)).thenThrow(ProfileNotFoundException(profileId = testProfile.id))
        expectedException.expect(ProfileNotFoundException::class.java)
        expectedException.expectMessage("Profile with id '${testProfile.id}' not found")
        tagService.createTag(testProfile.id, testTag)
        verify(profilServiceMock).getProfileById(testProfile.id)
        verify(tagRepositoryMock, never()).save(testTag)
    }

    @Test
    fun `getting all tags for a profile loads the associated tags from the database and returns them`() {
        `when`(profilServiceMock.exists(testProfile.id)).thenReturn(true)
        `when`(tagRepositoryMock.findAllByProfileId(testProfile.id)).thenReturn(setOf(testTag))
        val result = tagService.getAllTagsForProfile(testProfile.id)
        assertThat(result, `is`(setOf<Tag>(testTag)))
        verify(profilServiceMock).exists(testProfile.id)
        verify(tagRepositoryMock).findAllByProfileId(testProfile.id)
    }

    @Test
    fun `trying to get all tags for a non-existent profile throws a ProfileNotFoundException`() {

    }

    @Test
    fun `updating an existing tag updates that tag in the database`() {

    }

    @Test
    fun `trying to update a non-existent tag throws an EntityNotFoundException`() {

    }

    @Test
    fun `trying to update a tag for a non-existent profile throws a ProfileNotFoundException`() {

    }

    @Test
    fun `deleting an existing tag deletes that tag from the database`() {

    }

    @Test
    fun `trying to delete a non-existent tag throws an EntityNotFoundException`() {

    }

    @Test
    fun `trying to delete a tag for a non-existent profile throws a ProfileNotFoundException`() {

    }
}