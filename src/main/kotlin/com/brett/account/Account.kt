package com.brett.account

import com.brett.account.events.AccountUsernameModifiedEvent
import com.brett.account.events.AccountCreatedEvent
import com.brett.common.Aggregate
import com.brett.common.Event
import com.brett.common.exceptions.AggregateException

class Account : Aggregate {
    override val aggregateId: AccountId get() = accountId

    lateinit var userName: String
        private set
    lateinit var accountId: AccountId
        private set

    constructor(accountId: AccountId, userName: String) : this() {
        userName.guard { throw  AggregateException("User name cannot be null or empty")}
        raiseEvent(AccountCreatedEvent(accountId, userName))
    }

    constructor(events: List<Event>) : this(){
        events.forEach { event -> applyEvent(event) }
    }

    private constructor(){
        registrationEventRouter.register(AccountCreatedEvent::class, this::apply)
        registrationEventRouter.register(AccountUsernameModifiedEvent::class, this::apply)
    }

    fun modifyUserName(newUserName: String) {
        newUserName.guard { throw AggregateException("User name cannot be null or empty") }
        raiseEvent(AccountUsernameModifiedEvent(newUserName))
    }

    private fun apply(event: AccountCreatedEvent){
        this.accountId = event.accountId
        this.userName = event.userName
    }

    private fun apply(event: AccountUsernameModifiedEvent){
        this.userName = event.userName
    }

    override fun toString(): String {
        return "Account(accountId=$accountId, userName='$userName')"
    }
}

