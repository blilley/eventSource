package com.brett.common

import com.brett.account.Account
import com.brett.account.AccountId
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataMongoTest
class AggregateRepositoryTest{

    @Autowired
    private lateinit var eventCollection: EventCollection

    @Test
    fun save_ShouldSaveUncommittedEvents() {
        val aggregateRepository = AggregateRepository<Account>(eventCollection)
        val account = spy(Account(AccountId.new(), "Account"))

        val expectedEvents = account.uncommittedEvents.toList()

        aggregateRepository.save(account)

        val events = eventCollection.findAll()
        assertThat(events).hasSize(1)
        assertThat(events).isEqualTo(expectedEvents)
        verify(account).clearUncommittedEvents()
    }
}