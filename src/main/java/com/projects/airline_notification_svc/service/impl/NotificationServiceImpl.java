package com.projects.airline_notification_svc.service.impl;

import com.projects.airline_notification_svc.model.request.NotificationMessage;
import com.projects.airline_notification_svc.service.EmailService;
import com.projects.airline_notification_svc.service.NotificationService;
import com.projects.airline_notification_svc.service.PushNotificationService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl {

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private PushNotificationService pushNotificationService;

    public void processNotification(NotificationMessage message) {
        log.info("Processing notification for transaction ID: {}", message.getTransactionId());

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
