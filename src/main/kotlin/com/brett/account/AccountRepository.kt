package com.brett.account

import com.brett.common.AggregateId
import com.brett.common.persistence.AggregateRepository
import com.brett.common.persistence.EventCollection

class AccountRepository(eventCollection: EventCollection) : AggregateRepository<Account>(eventCollection) {
    override fun find(id: AggregateId): Account {
        return Account(getEventsForId(id))
    }
}