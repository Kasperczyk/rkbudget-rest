package de.kasperczyk.rkbudget.rest.tag

import de.kasperczyk.rkbudget.rest.AbstractDataJpaTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class TagRepositoryDataJpaTest : AbstractDataJpaTest() {

    @Autowired
    private lateinit var tagRepository: TagRepository

    @Test
    fun `tests todo`() {

    }
}