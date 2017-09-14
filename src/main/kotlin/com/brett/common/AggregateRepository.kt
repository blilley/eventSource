package com.brett.common

class AggregateRepository<in T: Aggregate>(private val eventCollection: EventCollection) {
    fun save(domain: T) {
        domain.uncommittedEvents.forEach {
            event -> eventCollection.save(event)
        }
        domain.clearUncommittedEvents()
    }
}