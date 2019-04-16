package com.acomodeo.mining.backend.event

import com.acomodeo.mining.model.Event
import org.springframework.stereotype.Service

@Service
class EventService(
    private val eventRepository: EventRepository
) {
    fun findOne(id: String): Event? = eventRepository.findById(id).orElse(null)

    fun findAllByRequestId(requestId: String) = eventRepository.findAllByRequestId(requestId)
}