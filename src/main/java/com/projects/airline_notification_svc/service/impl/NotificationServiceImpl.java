package com.projects.airline_notification_svc.service.impl;

import com.projects.airline_notification_svc.model.request.NotificationMessage;
import com.projects.airline_notification_svc.service.EmailService;
import com.projects.airline_notification_svc.service.NotificationService;
import com.projects.airline_notification_svc.service.PushNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private PushNotificationService pushNotificationService;

    public void processNotification(NotificationMessage message) {
        LOGGER.info("Processing notification for transaction ID: {}", message.getTransactionId());

        String formattedMessage = String.format(
                "Hello %s, your booking for flight %s (Seat %s) has been confirmed. Transaction ID: %s.",
                message.getUsername(),
                message.getFlightName(),
                message.getSeatNumber(),
                message.getTransactionId()
        );

        String notificationTitle = "Flight Booking Confirmed!";

        // Send the real notifications
        emailService.sendEmail(message.getEmail(), notificationTitle, formattedMessage);
        pushNotificationService.sendPushNotification(notificationTitle, formattedMessage);
    }
}
