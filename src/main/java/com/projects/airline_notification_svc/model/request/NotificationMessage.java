package com.projects.airline_notification_svc.model.request;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class NotificationMessage {

    private String username;
    private String mobileNumber;
    private String email;
    private String seatNumber;
    private String flightName;
    private String transactionId;
    private String message;
}