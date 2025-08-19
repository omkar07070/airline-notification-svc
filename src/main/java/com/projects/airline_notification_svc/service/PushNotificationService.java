package com.projects.airline_notification_svc.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Service for sending push notifications by calling the Gotify API directly.
 */

@Slf4j
@Service
public class PushNotificationService {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${notification.engine.gotify.url}")
    private String gotifyApiUrl;

    @Value("${notification.engine.gotify.token}")
    private String gotifyToken;

    /**
     * Sends a push notification directly to a Gotify server.
     *
     * @param title   The title of the notification.
     * @param message The body content of the notification.
     */
    public void sendPushNotification(String title, String message) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // The Gotify API does not use the token in the header for this public server,
            // so we add it to the URL.
            String urlWithToken = gotifyApiUrl + "?token=" + gotifyToken;

            // The Gotify API expects a JSON body with 'title', 'message', and 'priority'.
            Map<String, Object> requestBody = Map.of(
                    "title", title,
                    "message", message,
                    "priority", 5 // A normal priority
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            restTemplate.postForEntity(urlWithToken, entity, String.class);
            log.info("Successfully sent push notification directly to Gotify.");

        } catch (Exception e) {
            log.error("Failed to send push notification to Gotify. Error: {}", e.getMessage());
        }
    }
}