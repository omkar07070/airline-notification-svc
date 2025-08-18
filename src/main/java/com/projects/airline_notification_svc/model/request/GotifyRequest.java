package com.projects.airline_notification_svc.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO that represents the JSON request body sent to the Gotify server's /message endpoint.
 * This class is serialized into JSON by RestTemplate before being sent.
 *
 * Example JSON Body for Gotify API:
 * {
 *   "title": "Flight Booking Confirmed!",
 *   "message": "Hello Omkar, your booking...",
 *   "priority": 5
 * }
 */
@Data
@AllArgsConstructor // Generates a constructor with all arguments, useful for instantiation.
@NoArgsConstructor
public class GotifyRequest {

    private String title;
    private String message;
    private int priority;
}
