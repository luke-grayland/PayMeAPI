package com.LukeLabs.PayMeAPI.models.DTOs;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CardDTO {
    private UUID ID;
    private String cardNumber;
    private String cvv;
    private int expiryMonth;
    private int expiryYear;
    private double limit;
    private int userID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private String label;
}
