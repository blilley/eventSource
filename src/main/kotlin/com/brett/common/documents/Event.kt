package com.brett.common.documents

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "Event")
interface Event {
    val Id: UUID
}