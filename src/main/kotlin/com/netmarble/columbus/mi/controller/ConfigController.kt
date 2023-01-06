package com.netmarble.columbus.mi.controller

import com.netmarble.columbus.mi.domains.Config
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfigController(
    @Value("\${spring.kafka.template.default-topic}")
    val KAFKA_TOPIC: String,
    private var kafkaTemplate: KafkaTemplate<String, Config>
) {
    val log: Logger = LoggerFactory.getLogger(ConfigController::class.java)

    @PostMapping("/send")
    fun sendMessage(@Validated @RequestBody message: Config): ResponseEntity<Any> {
        return try {
            message.createTimestamp()
            log.info("Sending message to Kafka {}", message)
            kafkaTemplate.send(KAFKA_TOPIC, message)
            log.info("Message sent with success")

            ResponseEntity.ok().build()
        } catch (e: Exception) {
            log.error("Exception:", e)
            ResponseEntity.status(
                HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error to send message")
        }
    }

}