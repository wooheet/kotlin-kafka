package com.netmarble.columbus.mi.configs


import org.apache.kafka.common.serialization.StringSerializer
import com.netmarble.columbus.mi.domains.Config
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer
import java.io.Serializable

@Configuration
@EnableKafka
class Producer {

    @Value("\${spring.kafka.producer.bootstrap-servers}")
    lateinit var bootstrapServer: String

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Config> {
        val factory = DefaultKafkaProducerFactory<String, Config>(producerConfigs())
        return KafkaTemplate(factory)
    }

    @Bean
    fun producerConfigs(): Map<String, Serializable> =
        mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServer,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )
}