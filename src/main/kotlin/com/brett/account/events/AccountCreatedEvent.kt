package com.brett.account.events

import com.brett.account.AccountId
import com.brett.common.documents.Event
import org.springframework.data.annotation.Id

data class AccountCreatedEvent(@Id val accountId: AccountId, val userName: String) : Event