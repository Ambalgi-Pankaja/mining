package com.acomodeo.mining.backend.request

import com.acomodeo.mining.backend.activity.ActivityService
import com.acomodeo.mining.model.Request
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class RequestService(
    private val mongoTemplate: MongoTemplate,
    private val requestRepository: RequestRepository,
    private val activityService: ActivityService
) {
    // @Scheduled(cron = "0 15 03 00 * ?")
    fun groupByRequestIds() {
        val date = LocalDate.of(2019, 3, 15)

        val match1 = Aggregation.match(
            Criteria("additionalProperties.requestId").exists(true).ne("")
        )
        val match2 = Aggregation.match(Criteria("timeStamp").gt(date))
        val group = Aggregation.group("additionalProperties.requestId")
            .count().`as`("count")
        val project = Aggregation.project("count").and("_id").`as`("requestId")
        val sort = Aggregation.sort(Sort.Direction.DESC, "count")
        val aggregation = Aggregation.newAggregation(
            listOf(match1, match2, group, project, sort)
        ).withOptions(Aggregation.newAggregationOptions()
                .allowDiskUse(true)
                .build()
            )
        // supplying aggregation object to mongo template
        val results = mongoTemplate.aggregate(
            aggregation,
            "events",
            Request::class.java
        )
        // retrieving the results
        val traces = results.mappedResults

        val tempList = mutableListOf<Request>()

        // make a Request object to save in the database
        traces.forEach {
            val req = Request(
                requestId = it.requestId,
                count = it.count
            )
            tempList.add(req)
        }
        requestRepository.deleteAll()
        requestRepository.saveAll(tempList)
        activityService.makeActivities()
    }
}
