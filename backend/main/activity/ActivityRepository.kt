package com.acomodeo.mining.backend.activity

import com.acomodeo.mining.model.Activity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityRepository : MongoRepository<Activity, String>