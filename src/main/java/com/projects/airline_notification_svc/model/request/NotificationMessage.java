package com.projects.airline_notification_svc.model.request;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO / DTO that represents the message consumed from the Kafka topic.
 * Each field directly maps to a key in the incoming JSON message.
 *
 * Example JSON:
 * {
 *   "username": "Omkar",
 *   "mobileNumber": "9876543210",
 *   "email": "omkar@example.com",
 *   "seatNumber": "12A",
 *   "flightName": "SpiceJet 100",
 *   "transactionId": "TXN123456789",
 *   "message": "Your booking is confirmed!"
 * }
 */
@Data // Lombok annotation to automatically generate getters, setters, toString(), etc.
@NoArgsConstructor // Generates a no-argument constructor, which is required for deserialization.
public class NotificationMessage {

    private String username;
    private String mobileNumber;
    private String email;
    private String seatNumber;
    private String flightName;
    private String transactionId;
    private String message;
}