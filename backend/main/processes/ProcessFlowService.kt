package com.acomodeo.mining.backend.processes

import com.acomodeo.mining.backend.inputOutput.Formatting
import com.acomodeo.mining.backend.prom.PromService
import com.acomodeo.mining.model.ProcessFlow
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.stereotype.Service

@Service
class ProcessFlowService(
    private val mongoTemplate: MongoTemplate,
    private val processFlowRepository: ProcessFlowRepository,
    private val promService: PromService,
    private val formatting: Formatting
) {

    // @PostConstruct
    fun aggregateTraces() {
        val aggregation = Aggregation.newAggregation(
            // grouping by traces
            Aggregation.group("trace")
                .first("requestId").`as`("requestId")
                .count().`as`("count"),
            // project required fields
            Aggregation.project("requestId", "count")
                .and("_id").`as`("activities"),
            Aggregation.sort(Sort.Direction.ASC, "activities")
        )
        // applying the aggregation to ignites collection and
        // result to ProcessFlow data class
        val results = mongoTemplate.aggregate(
            aggregation,
            "activities",
            ProcessFlow::class.java
        )

        val traces = results.mappedResults
        val processList = mutableListOf<ProcessFlow>()
        // create a ProcessFlow object for the obtained result
        traces.forEach {
            val req = ProcessFlow(
                activities = it.activities,
                count = it.count,
                requestId = it.requestId
            )
            processList.add(req)
        }
        // delete old data and save the new result
        processFlowRepository.deleteAll()
        processFlowRepository.saveAll(processList)
        formatting.writeData(processList)
        promService.buildingProm()
    }
}