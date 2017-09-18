package com.brett.common

import kotlin.reflect.KClass

abstract class Aggregate {

    abstract val aggregateId: AggregateId

    private val _uncommittedEvents: MutableList<Event> = mutableListOf()
    protected val registrationEventRouter = EventRouter()
    val uncommittedEvents: List<Event> get() = _uncommittedEvents

    protected fun applyEvent(event: Event) {
        registrationEventRouter.dispatch(event)
    }

    protected fun raiseEvent(event: Event) {
        applyEvent(event)
        _uncommittedEvents.add(event)
    }

    fun clearUncommittedEvents() {
        _uncommittedEvents.clear()
    }

    protected inline fun String.guard(block: String.() -> Unit): String {
        if (this.isBlank())
            block()
        return this
    }

    @Suppress("UNCHECKED_CAST")
    class EventRouter {
        private val handlers: MutableMap<KClass<out Event>, (event: Event) -> Unit> = mutableMapOf()

        fun <T: Event> register(type: KClass<out T>, handler: (event: T) -> Unit) {
            handlers.put(type, handler as (event: Event) -> Unit)
        }

        fun dispatch(eventMessage: Event)
        {
            val handler = handlers[eventMessage::class] ?: throw RuntimeException("Unable to locate handler for aggregate event")
            handler(eventMessage)
        }
    }
}