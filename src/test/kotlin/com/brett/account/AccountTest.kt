package com.brett.account

import com.brett.account.events.AccountCreatedEvent
import com.brett.account.events.AccountUsernameModifiedEvent
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
        assertThat(account.uncommittedEvents[0]).isEqualTo(AccountCreatedEvent(accountId, "newAccount"))
    }

    @Test
    fun newAccount_ShouldThrowExceptionWhenEmptyUserName() {
        assertThatExceptionOfType(AggregateException::class.java).isThrownBy { Account(AccountId.new(), "")}
    }

    @Test
    fun modifyUserName_ShouldCreateAccountUsernameModifiedEvent() {
        val account = Account(AccountId.new(), "OriginalName")
        account.modifyUserName("NewName")

        assertThat(account.userName).isEqualTo("NewName")
        assertThat(account.uncommittedEvents).hasSize(2)
        assertThat(account.uncommittedEvents[1]).isInstanceOfSatisfying(AccountUsernameModifiedEvent::class.java, {
            assertThat(it.userName).isEqualTo("NewName")
        })
    }
}