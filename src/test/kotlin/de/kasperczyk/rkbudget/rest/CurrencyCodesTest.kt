package de.kasperczyk.rkbudget.rest

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class CurrencyCodesTest {

    @Test
    fun `test if currency codes are correct`() {
        assertThat(EURO, `is`("EUR"))
        assertThat(US_DOLLAR, `is`("USD"))
    }
}