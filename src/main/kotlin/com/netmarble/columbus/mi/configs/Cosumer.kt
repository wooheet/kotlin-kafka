package com.netmarble.columbus.mi.configs


import org.apache.kafka.common.serialization.StringDeserializer
import com.netmarble.columbus.mi.domains.Config
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class Cosumer {
    @Value("\${spring.kafka.consumer.bootstrap-servers}")
    lateinit var bootstrapServer: String
    @Value("\${spring.kafka.consumer.group-id")
    lateinit var groupId: String

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Config> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Config>()
        factory.consumerFactory = consumerFactory()
        return factory
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Config> =
        DefaultKafkaConsumerFactory(consumerConfigs())

    @Bean
    fun consumerConfigs(): Map<String, Any> =
        mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServer,
            ConsumerConfig.GROUP_ID_CONFIG to groupId,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest", // offest이 없거나 오류가 발생했을때 처리할 작업에 대한 설정
        )
}