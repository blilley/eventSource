package com.brett.account

import com.brett.common.documents.Event
import org.springframework.data.annotation.Id
import java.util.*

data class AccountCreatedEvent(@Id private val accountId: UUID, val userName: String) : Event{
    override val Id: UUID
        get() = accountId

}