package com.fetch.receipt_processor_challenge.config

import com.fetch.receipt_processor_challenge.dao.ReceiptRepository
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import de.bwaldvogel.mongo.MongoServer
import de.bwaldvogel.mongo.backend.memory.MemoryBackend
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoRepositories(basePackageClasses = [ReceiptRepository::class])
@Configuration
class MongoConfig {
    @Bean
    fun mongoTemplate(mongoClient: MongoClient): MongoTemplate {
        return MongoTemplate(mongoDbFactory(mongoClient))
    }

    @Bean
    fun mongoDbFactory(mongoClient: MongoClient): SimpleMongoClientDatabaseFactory {
        return SimpleMongoClientDatabaseFactory(mongoClient, "test")
    }

    @Bean(destroyMethod = "shutdown")
    fun mongoServer(): MongoServer {
        val mongoServer: MongoServer = MongoServer(MemoryBackend())
        mongoServer.bind()
        return mongoServer
    }

    @Bean(destroyMethod = "close")
    fun mongoClient(): MongoClient {
        return MongoClients.create("mongodb:/" + mongoServer().getLocalAddress())
    }
}