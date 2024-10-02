package com.fetch.receipt_processor_challenge.dao

import com.fetch.receipt_processor_challenge.dto.Receipt
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ReceiptRepository: MongoRepository<Receipt, String>