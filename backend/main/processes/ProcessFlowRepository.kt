package com.acomodeo.mining.backend.processes

import com.acomodeo.mining.model.ProcessFlow
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProcessFlowRepository : MongoRepository<ProcessFlow, String>