package com.brett.account

import com.brett.common.documents.Event
import org.springframework.data.annotation.Id
import java.util.*

data class AccountCreatedEvent(@Id private val accountId: AccountId, val userName: String) : Event{
    override val Id: AccountId
        get() = accountId

}