package com.brett.common.documents

import com.brett.common.AggregateId
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Event")
interface Event {
    val Id: AggregateId
}