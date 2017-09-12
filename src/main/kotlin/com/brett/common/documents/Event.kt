package com.brett.common.documents

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "Event")
interface Event {
}