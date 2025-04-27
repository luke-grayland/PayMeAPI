package com.LukeLabs.PayMeAPI.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "cards")
public class Card {
    @Id
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
}
