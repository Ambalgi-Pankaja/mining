package com.acomodeo.mining.backend.request

import com.acomodeo.mining.model.Request
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface RequestRepository : MongoRepository<Request, String>