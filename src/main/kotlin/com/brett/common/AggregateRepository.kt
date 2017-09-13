package com.brett.common

import com.brett.common.documents.Event
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface EventCollection : MongoRepository<Event, UUID>

class AggregateRepository<in T: Aggregate>(private val eventCollection: EventCollection) {
    fun save(domain: T) {
        domain.uncommittedEvents.forEach { event -> eventCollection.save(event) }
        domain.clearUncommittedEvents()
    }
}