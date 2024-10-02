package com.fetch.receipt_processor_challenge.controllers

import com.fetch.receipt_processor_challenge.dto.Receipt
import com.fetch.receipt_processor_challenge.services.ReceiptPointsService
import com.fetch.receipt_processor_challenge.services.ReceiptProcessorService
import jakarta.validation.Valid
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

val LOG: Logger = LoggerFactory.getLogger(ReceiptController::class.java)

@RestController
@RequestMapping("/receipts")
class ReceiptController(
    val receiptProcessorService: ReceiptProcessorService,
    val receiptPointsService: ReceiptPointsService
) {

    /**
     * Submits a receipt for processing
     *
     * @param receipt - the receipt to be processed
     * @return - the ID assigned to the receipt
     */
    @PostMapping("/process")
    fun create( @Valid @RequestBody receipt: Receipt): ResponseEntity<Map<String, String>> {
        LOG.info("Received process receipt request for {}", receipt)
        val receiptId = receiptProcessorService.processReceipt(receipt)
        receiptPointsService.processPoints(receipt)
        LOG.debug(receiptId)

        return ResponseEntity.ok().body(mapOf("id" to receiptId))
    }

    /**
     * Returns the points awarded for the receipt
     *
     * @param id - the ID of the receipt
     * @return - the points awarded for the receipt
     */
    @GetMapping("/{id}/points")
    fun readPoints(@PathVariable id: String): ResponseEntity<Map<String, String>> {
        LOG.info("Received request for receipt points with ID {}", id)
        val response = receiptPointsService.getPoints(id)
        LOG.debug(response.toString())
        if (response == null) {
            return ResponseEntity.badRequest().body(mapOf("description" to "No receipt found for that id"))
        }

        return ResponseEntity.ok().body(mapOf("points" to response.quantity.toString()))
    }
}