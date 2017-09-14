package com.brett.common

import com.brett.common.documents.Event
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface EventCollection : MongoRepository<Event, UUID>