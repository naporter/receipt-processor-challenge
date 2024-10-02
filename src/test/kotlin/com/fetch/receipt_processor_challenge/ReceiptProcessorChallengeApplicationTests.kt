package com.fetch.receipt_processor_challenge

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import kotlin.test.assertNotNull

@SpringBootTest
class ReceiptProcessorChallengeApplicationTests {
	@Autowired
	lateinit var applicationContext: ApplicationContext

	@Test
	fun contextLoads() {
		assertNotNull(applicationContext)
	}

}
