package com.brett.common

import com.brett.account.Account
import com.brett.account.AccountId
import com.brett.common.documents.Event
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class AggregateRepositoryTest{
    @Test
    fun save_ShouldSaveUncommittedEvents() {
        val eventCollection = mock<EventCollection>()
        val aggregateRepository = AggregateRepository<Account>(eventCollection)
        val account = spy(Account(AccountId.new(), "Account"))

        aggregateRepository.save(account)

        verify(eventCollection).save(any<Event>())
        verify(account).clearUncommittedEvents()
    }
}