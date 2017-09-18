package com.brett.common.persistence

import com.brett.common.AggregateId
import com.brett.common.Event
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "Event")
data class EventDocument(val aggregateId: AggregateId, val event: Event, val timeStamp: LocalDateTime)