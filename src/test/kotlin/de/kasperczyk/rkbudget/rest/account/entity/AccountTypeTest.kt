package de.kasperczyk.rkbudget.rest.account.entity

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class AccountTypeTest {

    @Test
    fun `check that AccountType constant values are correct and match the enum value's name`() {
        assertThat(CASH_ACCOUNT, `is`("CASH"))
        assertThat(GIRO_ACCOUNT, `is`("GIRO"))
        assertThat(SAVINGS_ACCOUNT, `is`("SAVINGS"))
        assertThat(CREDIT_ACCOUNT, `is`("CREDIT"))
        assertThat(CUSTOM_ACCOUNT, `is`("CUSTOM"))

        assertThat(CASH_ACCOUNT, `is`(AccountType.CASH.name))
        assertThat(GIRO_ACCOUNT, `is`(AccountType.GIRO.name))
        assertThat(SAVINGS_ACCOUNT, `is`(AccountType.SAVINGS.name))
        assertThat(CREDIT_ACCOUNT, `is`(AccountType.CREDIT.name))
        assertThat(CUSTOM_ACCOUNT, `is`(AccountType.CUSTOM.name))
    }
}