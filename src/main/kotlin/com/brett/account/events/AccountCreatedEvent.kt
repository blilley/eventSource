package com.brett.account.events

import com.brett.account.AccountId
import com.brett.common.Event

data class AccountCreatedEvent(val accountId: AccountId,
                               val userName: String) : Event