package com.brett.account

import com.brett.common.Aggregate
import com.brett.common.AggregateId
import com.brett.common.exceptions.AggregateException

class Account : Aggregate {
    override val aggregateId: AggregateId get() = accountId

    lateinit var userName: String
        private set
    lateinit var accountId: AccountId
        private set

    constructor(accountId: AccountId, userName: String) : this() {
        userName.guard { throw  AggregateException("User name cannot be null or empty")}

        raiseEvent(AccountCreatedEvent(accountId, userName))
    }

    private constructor(){
        registrationEventRouter.register(AccountCreatedEvent::class, this::apply)
    }

    private fun apply(event: AccountCreatedEvent){
        this.accountId = event.Id
        this.userName = event.userName
    }

    override fun toString(): String {
        return "Account(accountId=$accountId, userName='$userName')"
    }
}

