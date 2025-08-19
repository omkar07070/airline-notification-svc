package com.projects.airline_notification_svc.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor // Generates a constructor with all arguments, useful for instantiation.
@NoArgsConstructor
public class GotifyRequest {

    private String title;
    private String message;
    private int priority;
}
