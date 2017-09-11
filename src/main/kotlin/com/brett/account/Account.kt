package com.brett.account

import com.brett.common.documents.Event
import com.brett.common.exceptions.AggregateException
import kotlin.reflect.KClass

class Account {
    lateinit var userName: String
        private set
    lateinit var accountId: AccountId
        private set

    private val _uncommittedEvents: MutableList<Event> = mutableListOf()
    private val registrationEventRouter = EventRouter()

    val uncommittedEvents: List<Event> get() = _uncommittedEvents

    constructor(accountId: AccountId, userName: String) : this() {
        userName.guard { throw  AggregateException("User name cannot be null or empty")}

        raiseEvent(AccountCreatedEvent(accountId.value, userName))
    }

    private constructor(){
        registrationEventRouter.register(AccountCreatedEvent::class, this::apply)
    }

    private fun applyEvent(event: Event) {
        registrationEventRouter.dispatch(event)
    }

    private fun raiseEvent(event: Event) {
        applyEvent(event)
        _uncommittedEvents.add(event)
    }

    private fun apply(event: AccountCreatedEvent){
        this.accountId = AccountId(event.Id)
        this.userName = event.userName
    }

    override fun toString(): String {
        return "Account(accountId=$accountId, userName='$userName')"
    }

    private inline fun String.guard(block: String.() -> Unit): String {
        if (this.isBlank())
            block()
        return this
    }

    fun clearUncommittedEvents() {
        _uncommittedEvents.clear()
    }

    @Suppress("UNCHECKED_CAST")
    class EventRouter {
        private val handlers: MutableMap<KClass<out Event>, (event: Event) -> Unit> = mutableMapOf()

        fun <T: Event> register(type: KClass<out T>, handler: (event: T) -> Unit) {
            handlers.put(type, handler as (event: Event) -> Unit)
        }

        fun dispatch(eventMessage: Event)
        {
            val handler = handlers[eventMessage::class] ?: throw RuntimeException()
            handler(eventMessage)
        }
    }
}

