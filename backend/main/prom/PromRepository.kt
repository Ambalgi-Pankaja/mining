package com.acomodeo.mining.backend.prom

import com.acomodeo.mining.model.Prom
import com.acomodeo.semanticid.SemanticId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PromRepository : MongoRepository<Prom, SemanticId>