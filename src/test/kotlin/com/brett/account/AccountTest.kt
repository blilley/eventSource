package com.brett.account

import com.brett.common.exceptions.AggregateException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Test

class AccountTest{
    @Test
    fun newAccount_ShouldCreateEventAndAddedItToUncommitted() {
        val accountId = AccountId.new()

        val account = Account(accountId, "newAccount")

        assertThat(account.userName).isEqualTo("newAccount")
        assertThat(account.accountId).isEqualTo(accountId)
        assertThat(account.uncommittedEvents).hasSize(1)
        assertThat(account.uncommittedEvents[0]).isEqualTo(AccountCreatedEvent(accountId.value, "newAccount"))
    }

    @Test
    fun newAccount_ShouldThrowExceptionWhenEmptyUserName() {
        assertThatExceptionOfType(AggregateException::class.java).isThrownBy { Account(AccountId.new(), "")}
    }
}