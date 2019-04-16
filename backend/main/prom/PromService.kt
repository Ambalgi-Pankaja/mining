package com.acomodeo.mining.backend.prom

import com.acomodeo.foundation.mongo.filters.EntityRepository
import com.acomodeo.mining.backend.processes.ProcessFlowRepository
import com.acomodeo.mining.model.Edge
import com.acomodeo.mining.model.Node
import com.acomodeo.mining.model.Prom
import com.acomodeo.semanticid.SemanticId
import org.springframework.stereotype.Service

@Service
class PromService(
    private val promRepository: PromRepository,
    private val processFlowRepository: ProcessFlowRepository
) : EntityRepository<Prom, SemanticId> {
    override fun delete(id: SemanticId) = promRepository.deleteById(id)

    override fun exists(id: SemanticId): Boolean = promRepository.existsById(id)

    override fun findOne(id: SemanticId): Prom? = promRepository.findById(id).orElse(null)

    override fun insert(toSave: Prom): Prom = promRepository.save(toSave)

    override fun update(toSave: Prom): Prom = promRepository.save(toSave)

    fun getAll(): List<Prom> = promRepository.findAll()

    // @PostConstruct
    fun buildingProm() {
        // get processes
        val processes = processFlowRepository.findAll()
            .filterNotNull().sortedByDescending { it.count }

        val nodeList = mutableListOf<Node>()
        val edgeList = mutableListOf<Edge>()
        var edgeId = 1
        var nodeId = 2
        val internetNode = Node (
            id = 1.toString(),
            label = "INTERNET",
            level = 1.toString(),
            group = 1.toString(),
            count = 1
        )
        nodeList.add(internetNode)
        processes.forEach {
            val tempNodeList = mutableListOf<Node>()
            val nodes = it.activities.split("->")
            val nodeCount = it.count.toInt()
            var level = 2
            tempNodeList.add(internetNode)
            var previousLabel = ""
            var previousLevel = ""
            nodes.forEach {
                val node = Node(
                   id = nodeId++.toString(),
                   label = it,
                   level = if(previousLabel == it) previousLevel
                   else level++.toString(),
                   group = getServiceGroup(it).toString(),
                   count = nodeCount
                )
                previousLabel = node.label
                previousLevel = node.level
                tempNodeList.add(node)
                nodeList.add(node)
            }

            for (i in 0 until tempNodeList.size-1) {
                var fromLabel = ""
                var toLabel = ""
                var from = ""
                var to = ""
                var a = i
                do {
                    if (tempNodeList[i].label == tempNodeList[i+1].label) {
                        from = tempNodeList[a-1].id
                        to = tempNodeList[i+1].id
                        fromLabel = tempNodeList[a-1].label
                        toLabel = tempNodeList[i+1].label
                        a--
                    } else {
                        from = tempNodeList[i].id
                        to = tempNodeList[i+1].id
                        fromLabel = tempNodeList[i].label
                        toLabel = tempNodeList[i+1].label
                    }
                } while (fromLabel == toLabel)

                val edges = Edge(
                    id = edgeId++.toString(),
                    from = from,
                    to = to,
                    label = it.count.toString(),
                    name = toLabel
                )
                edgeList.add(edges)
            }
        }
        val prom = Prom(
            nodes = nodeList,
            edges = edgeList
        )
        promRepository.deleteAll()
        promRepository.save(prom)
    }

    private fun getServiceGroup(service: String): Int {
        val first = service.split('-', ' ')[0]

        return when (first) {
            "gds" -> 11
            "lightning" -> 2
            "exchange" -> 3
            "booking" -> 4
            "beacon" -> 5
            "htng" -> 6
            "coordinator" -> 7
            "pixie" -> 8
            "portal" -> 9
            "rfp" -> 10
            "srs" -> 12
            "stop" -> 13
            else -> 1
        }
    }
}