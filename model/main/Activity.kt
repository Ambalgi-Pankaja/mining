package com.acomodeo.mining.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

data class GroupTrace(
    val trace: String? = null,

    val requestId: String? = null,

    val timeStamp: Date
)

@Document(collection = Activity.mongoCollection)
data class Activity(

    @Id
    val id: String? = null,

    val trace: String? = null,

    val requestId: String? = null,

    val count: Int? = null
) {
    companion object {
        private const val collection = "activities"
        const val mongoCollection = collection
    }
}

@Document(collection = ProcessFlow.mongoCollection)
data class ProcessFlow(

    @Id
    val id: String? = null,

    val activities: String,

    val count: Int,

    val requestId: String
) {
    companion object {
        private const val collection = "processes"
        const val mongoCollection = collection
    }
}