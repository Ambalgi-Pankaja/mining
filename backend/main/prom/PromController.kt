package com.acomodeo.mining.backend.prom

import com.acomodeo.foundation.mongo.filters.AbstractCRUDController
import com.acomodeo.mining.model.Prom
import com.acomodeo.semanticid.SemanticId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("prom")
class PromController(
    override val mongoTemplate: MongoTemplate,
    override val repo: PromService
) : AbstractCRUDController<Prom, SemanticId, Prom, PromService>() {

    override fun Prom.copyWithId(id: SemanticId): Prom = copy(id = id)

    override fun Prom.generateId(): SemanticId = generateId()

    override fun Prom.getId(): SemanticId = id

    override fun Prom.toModel(): Prom = this

    override fun Prom.toView(): Prom = this

    @GetMapping("/{id}")
    fun findById(
        @PathVariable("id") id: SemanticId
    ): Prom? = repo.findOne(id)

    @GetMapping
    fun getAll(): List<Prom> = repo.getAll()
}
