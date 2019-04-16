package com.acomodeo.mining.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = Request.mongoCollection)
data class Request(
    @Id
    val id: String? = null,
    val requestId: String,
    val count: Int
) {
    companion object {
        private const val collection = "request"
        const val mongoCollection = collection
    }
}