package com.fetch.receipt_processor_challenge.services

import com.fetch.receipt_processor_challenge.dao.PointsRepository
import com.fetch.receipt_processor_challenge.dto.*
import com.fetch.receipt_processor_challenge.transformers.PointsTransformer
import org.springframework.stereotype.Service

@Service
class ReceiptPointsService(
    val pointsRepository: PointsRepository,
    val pointsTransformer: PointsTransformer
) {

    /**
     * Uses the [PointsTransformer] to transform the receipt into [Points].
     * Then calls the [pointsRepository] to persist the [Points]
     */
    fun processPoints(receipt: Receipt) {
        pointsRepository.insert(pointsTransformer.transform(receipt))
    }

    /**
     * Calls to the [PointsRepository] to get the points
     * given the [id]
     *
     * @param id - the ID of the persisted points
     * @return [Points]
     */
    fun getPoints(id: String): Points? {
        return pointsRepository.findById(id).orElse(null)
    }
}