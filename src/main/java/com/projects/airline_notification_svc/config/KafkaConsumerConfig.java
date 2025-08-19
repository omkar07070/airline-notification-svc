package com.projects.airline_notification_svc.config;


import com.projects.airline_notification_svc.model.request.NotificationMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;


import java.util.HashMap;
import java.util.Map;

/**
 * Java-based configuration for the Kafka Consumer.
 *
 * The @EnableKafka annotation is crucial; it enables the detection of @KafkaListener
 * annotations on Spring-managed beans.
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    // Injecting Kafka properties from application.yml
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    /**
     * Creates the ConsumerFactory which is responsible for setting up the strategy
     * for creating Kafka Consumer instances.
     *
     * @return a configured ConsumerFactory for creating consumers that listen for
     *         String keys and NotificationMessage values.
     */
    @Bean
    public ConsumerFactory<String, NotificationMessage> consumerFactory() {
        // A map to hold the consumer configuration properties.
        Map<String, Object> props = new HashMap<>();

        // Core Kafka consumer properties
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Set up the JsonDeserializer for the message value.
        // It needs to know the target class and to trust the package it's in.
        JsonDeserializer<NotificationMessage> jsonDeserializer = new JsonDeserializer<>(NotificationMessage.class);
        jsonDeserializer.setRemoveTypeHeaders(false);
        jsonDeserializer.addTrustedPackages("com.projects.airline_notification_svc");
        jsonDeserializer.setUseTypeMapperForKey(true);

        // Return a new DefaultKafkaConsumerFactory with the key deserializer (String)
        // and our configured value deserializer (JSON).
        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                jsonDeserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationMessage> kafkaListenerContainerFactory(ConsumerFactory<String, NotificationMessage> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, NotificationMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}