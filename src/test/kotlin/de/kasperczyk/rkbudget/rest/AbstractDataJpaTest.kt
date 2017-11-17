package de.kasperczyk.rkbudget.rest

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.EntityManager

@RunWith(SpringRunner::class)
@DataJpaTest
abstract class AbstractDataJpaTest {

    @Autowired
    protected lateinit var entityManager: EntityManager
}