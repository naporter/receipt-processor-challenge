package com.fetch.receipt_processor_challenge.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fetch.receipt_processor_challenge.dto.Receipt
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReceiptProcessorServiceTest {

    private val objectMapper = ObjectMapper().registerKotlinModule()

    @Autowired
    lateinit var receiptProcessorService: ReceiptProcessorService

    /**
     * Tests that the ID is being generated
     */
    @Test
    fun processReceipt_test() {
        val targetReceipt = this::class.java.getResource("/requests/receipts/targetReceipt.json")?.readText()
        val targetReceiptObj = objectMapper.readValue(targetReceipt, Receipt::class.java)

        receiptProcessorService.processReceipt(targetReceiptObj)

        assertNotNull(targetReceiptObj.id)

    }
}