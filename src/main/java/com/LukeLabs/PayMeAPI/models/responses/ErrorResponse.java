package com.LukeLabs.PayMeAPI.models.responses;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    public ErrorResponse(String errorMessage) {
        this.timestamp = LocalDateTime.now();
        this.errorMessage = errorMessage;
    }

    private final LocalDateTime timestamp;
    private final String errorMessage;
}
