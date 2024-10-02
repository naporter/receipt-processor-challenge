package com.fetch.receipt_processor_challenge.services

import com.fetch.receipt_processor_challenge.dao.ReceiptRepository
import com.fetch.receipt_processor_challenge.dto.Receipt
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReceiptProcessorService(
    val receiptRepository: ReceiptRepository
) {

    /**
     * Calls the [ReceiptRepository] to persist the [Receipt].
     *
     * @param receipt
     * @return - the receipt id
     */
    fun processReceipt(receipt: Receipt): String {
        val randomId = UUID.randomUUID().toString()
        receipt.id = randomId
        receiptRepository.insert(receipt)
        return randomId
    }

}