package com.fetch.receipt_processor_challenge.dao

import com.fetch.receipt_processor_challenge.dto.Points
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PointsRepository: MongoRepository<Points, String>