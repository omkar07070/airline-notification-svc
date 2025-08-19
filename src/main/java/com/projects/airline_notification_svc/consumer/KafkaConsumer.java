package com.projects.airline_notification_svc.consumer;

import com.projects.airline_notification_svc.model.request.NotificationMessage;
import com.projects.airline_notification_svc.service.NotificationService;
import com.projects.airline_notification_svc.service.impl.NotificationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private NotificationServiceImpl notificationService;

    /**
     * Listens for messages on the "notification-topic".
     * Spring Kafka automatically deserializes the JSON into a NotificationMessage POJO.
     * @param message The deserialized message object from Kafka.
     */
    @KafkaListener(topics = "notification-topic", groupId = "notification-service")
    public void consume(NotificationMessage message) {
        LOGGER.info("Consumed Kafka message -> {}", message);
        notificationService.processNotification(message);
    }
}
