package com.fetch.receipt_processor_challenge.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fetch.receipt_processor_challenge.dto.Receipt
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReceiptControllerTest {

    lateinit var receiptProcessUrl: String
    lateinit var receiptPointsUrl: String
    private val objectMapper = ObjectMapper().registerKotlinModule()

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @BeforeEach
    fun setup() {
        receiptProcessUrl = "http://localhost:$port/receipts/process"
        receiptPointsUrl = "http://localhost:$port/receipts/{id}/points"
    }

    /**
     * E2E test for a successful receipt creation
     */
    @Test
    fun createSuccess_test() {
        val targetReceipt = this::class.java.getResource("/requests/receipts/targetReceipt.json")?.readText()
        val targetReceiptObj = objectMapper.readValue(targetReceipt, Receipt::class.java)

        val response = restTemplate.postForEntity(receiptProcessUrl, targetReceiptObj, String::class.java)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertNotNull(objectMapper.readTree(response.body).get("id"))
    }

    /**
     * E2E test for a failed receipt creation
     */
    @Test
    fun createFail_test() {
        val targetReceipt = this::class.java.getResource("/requests/receipts/targetReceipt.json")?.readText()
        val targetReceiptObj = objectMapper.readValue(targetReceipt, Receipt::class.java)
        val targetReceiptObjCopy = targetReceiptObj.copy(
            retailer = "203kdnas#$^^&*(%@#$%sdvi0"
        )

        val response = restTemplate.postForEntity(receiptProcessUrl, targetReceiptObjCopy, String::class.java)
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("The receipt is invalid", objectMapper.readTree(response.body).get("description").asText())
    }

    /**
     * E2E test for a successful points retrieval
     */
    @Test
    fun readPointsSuccess_test() {
        val targetReceipt = this::class.java.getResource("/requests/receipts/targetReceipt.json")?.readText()
        val targetReceiptObj = objectMapper.readValue(targetReceipt, Receipt::class.java)

        val receiptResponse = restTemplate.postForEntity(receiptProcessUrl, targetReceiptObj, String::class.java)
        val receiptId = objectMapper.readTree(receiptResponse.body).get("id").asText()

        val pointsResponse = restTemplate.getForEntity(receiptPointsUrl, String::class.java, receiptId)

        assertEquals(HttpStatus.OK, pointsResponse.statusCode)
        assertNotNull(objectMapper.readTree(pointsResponse.body).get("points").asDouble())
    }

    /**
     * E2E test for a failed points retrieval
     */
    @Test
    fun readPointsFail_test() {
        val receiptId = UUID.randomUUID().toString()

        val pointsResponse = restTemplate.getForEntity(receiptPointsUrl, String::class.java, receiptId)

        assertEquals(HttpStatus.BAD_REQUEST, pointsResponse.statusCode)
        LOG.info(pointsResponse.toString())
        assertEquals("No receipt found for that id", objectMapper.readTree(pointsResponse.body).get("description").asText())
    }

}