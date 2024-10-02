package com.fetch.receipt_processor_challenge.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fetch.receipt_processor_challenge.dto.Receipt
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReceiptsPointsServiceTest {

    private val objectMapper = ObjectMapper().registerKotlinModule()

    @Autowired
    lateinit var receiptPointsService: ReceiptPointsService

    /**
     * Tests that points are computed accurately and persisted.
     * Tests that receipts are persisted.
     */
    @Test
    fun getPoints_test() {
        val targetReceipt = this::class.java.getResource("/requests/receipts/targetReceipt.json")?.readText()

        val targetReceiptObj = objectMapper.readValue(targetReceipt, Receipt::class.java)
        val targetReceiptId = UUID.randomUUID().toString()
        targetReceiptObj.id = targetReceiptId
        receiptPointsService.processPoints(targetReceiptObj)
        val pointsObjTarget = receiptPointsService.getPoints(targetReceiptId)

        assertEquals(28.0, pointsObjTarget?.quantity)

        val mAndMReceipt = this::class.java.getResource("/requests/receipts/mAndMCornerMarketReceipt.json")?.readText()

        val mAndMReceiptObj = objectMapper.readValue(mAndMReceipt, Receipt::class.java)
        val mAndMReceiptId = UUID.randomUUID().toString()
        mAndMReceiptObj.id = mAndMReceiptId
        receiptPointsService.processPoints(mAndMReceiptObj)
        val pointsObjMAndM = receiptPointsService.getPoints(mAndMReceiptId)

        assertEquals(109.0, pointsObjMAndM?.quantity)
    }
}