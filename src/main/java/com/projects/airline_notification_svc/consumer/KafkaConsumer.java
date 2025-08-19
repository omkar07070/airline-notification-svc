package com.projects.airline_notification_svc.consumer;

import com.projects.airline_notification_svc.model.request.NotificationMessage;
import com.projects.airline_notification_svc.service.NotificationService;
import com.projects.airline_notification_svc.service.impl.NotificationServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    @Autowired
    private NotificationServiceImpl notificationService;


    @KafkaListener(topics = "notification-topic", groupId = "notification-service")
    public void consume(NotificationMessage message) {
        log.info("Consumed Kafka message -> {}", message);
        notificationService.processNotification(message);
    }
}
