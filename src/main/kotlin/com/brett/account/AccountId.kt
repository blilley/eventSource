package com.brett.account

import com.brett.common.AggregateId
import java.util.*

class AccountId (val value: UUID) : AggregateId {
    companion object {
        fun new() : AccountId = AccountId(UUID.randomUUID())
    }

    override fun toString(): String {
        return "AccountId(value=$value)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AccountId

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

