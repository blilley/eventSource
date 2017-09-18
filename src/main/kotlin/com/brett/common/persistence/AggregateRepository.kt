package com.brett.common.persistence

import com.brett.common.Aggregate
import com.brett.common.AggregateId
import com.brett.common.Event
import java.time.LocalDateTime

abstract class AggregateRepository<T : Aggregate>(private val eventCollection: EventCollection) {
    fun save(domain: T) {
        domain.uncommittedEvents.forEach { event ->
                eventCollection.save(EventDocument(domain.aggregateId, event, LocalDateTime.now()))
        }
        domain.clearUncommittedEvents()
    }

    abstract fun find(id: AggregateId): T

    protected fun getEventsForId(id: AggregateId): List<Event>{
        return eventCollection.findAllByAggregateIdOrderByTimeStamp(id).map{
            eventDocument -> eventDocument.event
        }
    }
}