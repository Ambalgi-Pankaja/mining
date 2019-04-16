package com.acomodeo.mining.backend.event

import com.acomodeo.mining.model.Event
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("events")
class EventController(
    private val eventService: EventService
) {
    @GetMapping("/{id}")
    fun findEventById(
        @PathVariable("id") id: String
    ): Event? = eventService.findOne(id = id)

    @GetMapping
    fun findAllByRequestId(
        @RequestParam("requestId") requestId: String
    ): List<Event>? = eventService.findAllByRequestId(requestId = requestId)
}
