package com.acomodeo.mining.model

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = Event.mongoCollection)
data class Event(
    val id: String,

    val className: String,

    val timeStamp: Date,

    val sender: String? = null,

    val receiver: String? = null,

    val severity: String? = null,

    val duration: Int? = null,

    val additionalProperties: AdditionalProperty,

    val region: String? = null
) {
    companion object {
        private const val collection = "events"
        const val mongoCollection = collection
    }
}

data class AdditionalProperty(
    val requestMethod: String? = null,

    val senderIP: String? = null,

    @Indexed
    val requestId: String? = null,

    val requestUrl: String? = null,

    val queryString: String? = null,

    val serviceTrace: String? = null,

    val requestHeaders: String? = null,

    val statusCode: String? = null,

    val responseHeaders: String? = null
)
