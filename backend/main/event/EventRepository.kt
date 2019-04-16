package com.acomodeo.mining.backend.event

import com.acomodeo.mining.model.Event
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : MongoRepository<Event, String> {

    @Query(
            """
                {
                    "additionalProperties.requestId": ?0
                }
            """
    )
    fun findAllByRequestId(requestId: String?): List<Event>
}
