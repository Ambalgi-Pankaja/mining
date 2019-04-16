package com.acomodeo.mining.backend.activity

import com.acomodeo.mining.backend.event.EventRepository
import com.acomodeo.mining.backend.request.RequestRepository
import com.acomodeo.mining.backend.processes.ProcessFlowService
import com.acomodeo.mining.model.GroupTrace
import com.acomodeo.mining.model.Activity
import org.springframework.stereotype.Service

@Service
class ActivityService(
    private val eventRepository: EventRepository,
    private val requestRepository: RequestRepository,
    private val activityRepository: ActivityRepository,
    private val processFlowService: ProcessFlowService
) {

    val regexForHttp = """http:\/\/[a-z0-9\-\/]*.\w+.\w+\/""".toRegex()
    val api = """api\/""".toRegex()
    val semanticid = """\/aco:[a-z\-]*:[a-z\-0-9A-Z\_]*""".toRegex()
    val cont = """Contact\/(\w+)""".toRegex()
    val query = """\?.*${'$'}""".toRegex()
    val hash = """\#.*${'$'}""".toRegex()
    val settings = """\/accounts\/me\/settings\/gds""".toRegex()
    val pics = """pictures\/.*""".toRegex()
    val ams = """\/aco%.*""".toRegex()
    val pixie = """(.*).jpg""".toRegex()
    val propsvc = """properties\/.*""".toRegex()
    val bookings = """bookings\/.*""".toRegex()
    val users = """users\/.*""".toRegex()
    val pciproxy = """pciproxy\/.*""".toRegex()
    val apt = """apartments\/.*""".toRegex()
    val apsvc = """apartment-types\/.*""".toRegex()
    val offReq = """offerrequests\/.*""".toRegex()
    val offers = """offers\/.*""".toRegex()
    val contacts = """contacts\/.*""".toRegex()
    val restrictions = """restrictions\/.*""".toRegex()

    // @PostConstruct
    fun makeActivities() {
        // Get the list of RequestIDs
        val requestIds = requestRepository.findAll()
        // Mutable list of Activity
        val activities = mutableListOf<Activity>()

        requestIds.forEach {
            // find events for each requestId
            val events = eventRepository.findAllByRequestId(it.requestId)
            // Mutable list of GroupTrace
            val activityList = mutableListOf<GroupTrace>()
            // Combine few fields to make a trace and
            // create a GroupTrace object, an intermediary object
            // formatURL function is used to normalize the URL
            events.forEach {
                val groupTrace = GroupTrace(
                    requestId = it.additionalProperties.requestId,
                    trace = it.receiver + "-" +
                                    it.additionalProperties.requestMethod + "-/" +
                                    formatURL(it.additionalProperties.requestUrl),
                    timeStamp = it.timeStamp
                )
                activityList.add(groupTrace)
            }
            // From the list of groupTraces of same requestId,
            // sort by time and
            // concatenate the service processes,
            // to form a sequence of activities for each requestId
            val traces = activityList.sortedByDescending {
                it.timeStamp
            }.map {
                it.trace
            }.joinToString("->")
            // create an Activity object with the sequence of activities
            // and requestId
            val activity = Activity(
                trace = traces,
                requestId = it.requestId
            )
            activities.add(activity)
        }
        // delete existing data and save the new results.
        activityRepository.deleteAll()
        activityRepository.saveAll(activities)
        processFlowService.aggregateTraces()
    }

    private fun formatURL(url: String?): String? = url
            ?.replace(regexForHttp, "")
            ?.replace(api, "")
            ?.replace(cont, "")
            ?.replace(query, "")
            ?.replace(hash, "")
            ?.replace(semanticid, "/id")
            ?.replace(settings, "/settings")
            ?.replace(pics, "pictures")
            ?.replace(ams, "")
            ?.replace(pixie, "apartment/slideshow.jpg")
            ?.replace(propsvc, "properties/id")
            ?.replace(apsvc, "apartment-types/id")
            ?.replace(offReq, "offerrequests")
            ?.replace(offers, "offers")
            ?.replace(contacts, "com/contacts")
            ?.replace(apt, "apartments")
            ?.replace(bookings, "bookings/id")
            ?.replace(pciproxy, "pciproxy/id")
            ?.replace(users, "users/id")
            ?.replace(restrictions, "restrictions/id")
}