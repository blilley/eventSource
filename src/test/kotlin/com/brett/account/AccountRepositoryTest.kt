package com.brett.account

import com.brett.account.events.AccountCreatedEvent
import com.brett.account.events.AccountUsernameModifiedEvent
import com.brett.common.persistence.EventCollection
import com.brett.common.persistence.EventDocument
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDateTime

@RunWith(SpringRunner::class)
@DataMongoTest
class AccountRepositoryTest {

    @Autowired
    private lateinit var eventCollection: EventCollection

    @Test
    fun save_ShouldSaveUncommittedEvents() {
        val aggregateRepository = AccountRepository(eventCollection)
        val account = spy(Account(AccountId.new(), "Account"))

        val expectedEvents = account.uncommittedEvents.toList()

        aggregateRepository.save(account)

        val events = eventCollection.findAll()
        assertThat(events).hasSize(1)
        assertThat(events).isEqualTo(expectedEvents)
        verify(account).clearUncommittedEvents()
    }

    @Test
    fun find_WithDataShouldReturnAggregate() {
        val accountId = AccountId.new()
        eventCollection.save(EventDocument(accountId, AccountCreatedEvent(accountId, "Billy"), LocalDateTime.now()))
        eventCollection.save(EventDocument(accountId, AccountUsernameModifiedEvent("Bob"), LocalDateTime.now().plusMinutes(20)))
        val aggregateRepository = AccountRepository(eventCollection)

        val account = aggregateRepository.find(accountId)

        assertThat(account.userName).isEqualTo("Bob")
        assertThat(account.accountId).isEqualTo(accountId)
    }
}