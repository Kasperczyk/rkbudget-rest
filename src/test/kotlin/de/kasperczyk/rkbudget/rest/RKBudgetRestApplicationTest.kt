package de.kasperczyk.rkbudget.rest

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate

@RunWith(SpringRunner::class)
@SpringBootTest
class RKBudgetRestApplicationTest {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

	@Test
    fun `the spring context should load`() {
    }

    @Test
    fun `an ObjectMapper bean that has the JavaTimeModule registered should be available`() {
        val objectMapper = applicationContext.getBean("objectMapper", ObjectMapper::class.java)
        val date = LocalDate.of(2017, 11, 24)
        val jsonDate = objectMapper.writeValueAsString(date)
        assertThat(date, `is`(objectMapper.readValue(jsonDate, LocalDate::class.java)))
	}
}
