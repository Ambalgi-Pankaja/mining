package com.acomodeo.mining.model

import com.acomodeo.semanticid.SemanticId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Prom(
    @Id
    val id: SemanticId = SemanticId(collection = "prom"),

    val nodes: List<Node>,

    val edges: List<Edge>
)

data class Node(

    val id: String,

    val label: String,

    val group: String,

    val level: String,

    val count: Int
)

data class Edge(

    val id: String,

    val from: String,

    val to: String,

    val label: String,

    val name: String
)