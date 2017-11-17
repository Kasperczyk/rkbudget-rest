package de.kasperczyk.rkbudget.rest.account.entity

import de.kasperczyk.rkbudget.rest.profile.entity.Profile
import de.kasperczyk.rkbudget.rest.testProfile
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.time.LocalDate

class AccountTest {

    @Test
    fun `test CustomAccount's equals() method`() {
        val customAccount = CustomAccount("custom account", testProfile, LocalDate.now())
        val equalCustomAccount = CustomAccount("custom account", testProfile, LocalDate.now())
        assertThat(customAccount, `is`(equalCustomAccount))

        val unequalCustomAccount = CustomAccount("other custom account", Profile(),
                LocalDate.of(2010, 1, 1))
        assertThat(customAccount, `is`(not(unequalCustomAccount)))
    }

    @Test
    fun `test CashAccount's equals() method`() {
        val cashAccount = CashAccount("cash account", testProfile)
        val equalCashomAccount = CashAccount("cash account", testProfile)
        assertThat(cashAccount, `is`(equalCashomAccount))

        val unequalCashomAccount = CashAccount("other cash account", Profile())
        assertThat(cashAccount, `is`(not(unequalCashomAccount)))
    }

    @Test
    fun `test CreditAccount's equals() method`() {
        val creditAccount = CreditAccount("credit account", testProfile, LocalDate.now(),
                "issuer", "credit card number")
        val equalCreditAccount = CreditAccount("credit account", testProfile, LocalDate.now(),
                "issuer", "credit card number")
        assertThat(creditAccount, `is`(equalCreditAccount))

        val unequalCreditAccount = CreditAccount("other credit account", Profile(),
                LocalDate.of(2010, 1, 1),
                "other issuer", "other credit card number")
        assertThat(creditAccount, `is`(not(unequalCreditAccount)))
    }

    @Test
    fun `test GiroAccount's equals() method`() {
        val giroAccount = GiroAccount("giro account", testProfile, LocalDate.now(),
                "institute", "iban")
        val equalGiroAccount = GiroAccount("giro account", testProfile, LocalDate.now(),
                "institute", "iban")
        assertThat(giroAccount, `is`(equalGiroAccount))

        val unequalGiroAccount = GiroAccount("other giro account", Profile(),
                LocalDate.of(2010, 1, 1),
                "other institute", "other iban")
        assertThat(giroAccount, `is`(not(unequalGiroAccount)))
    }

    @Test
    fun `test SavingsAccount's equals() method`() {
        val savingsAccount = SavingsAccount("savings account", testProfile, LocalDate.now(),
                "institute", "iban")
        val equalsSavingsAccount = SavingsAccount("savings account", testProfile, LocalDate.now(),
                "institute", "iban")
        assertThat(savingsAccount, `is`(equalsSavingsAccount))

        val unequalSavingsAccount = SavingsAccount("other savings account", Profile(),
                LocalDate.of(2010, 1, 1),
                "other institute", "other iban")
        assertThat(savingsAccount, `is`(not(unequalSavingsAccount)))
    }
}