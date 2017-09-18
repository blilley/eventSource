package com.brett.account.events

import com.brett.common.Event

data class AccountUsernameModifiedEvent(val userName: String) : Event