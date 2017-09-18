package com.brett.common.persistence

import com.brett.common.AggregateId
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface EventCollection : MongoRepository<EventDocument, UUID>{
    fun findAllByAggregateIdOrderByTimeStamp(id: AggregateId) : List<EventDocument>
}