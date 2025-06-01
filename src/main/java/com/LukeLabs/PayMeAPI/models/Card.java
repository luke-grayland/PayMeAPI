package com.LukeLabs.PayMeAPI.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private String label;

    @Override
    public String toString() {
        return String.format("ID: %s " +
                "Card Number: %s " +
                "CVV: %s " +
                "Expiry Month: %d " +
                "Expiry Year: %d " +
                "Limit: %.2f " +
                "User ID: %d " +
                "Start Date: %s " +
                "End Date: %s " +
                "Status: %s " +
                "Label: %s ",
                ID.toString(),
                ObfuscatedSensitiveValue(cardNumber),
                ObfuscatedSensitiveValue(cvv),
                expiryMonth,
                expiryYear,
                limit,
                userID,
                startDate,
                endDate,
                status,
                label
        );
    }

    private static String ObfuscatedSensitiveValue(String value) {
        if(value == null || value.isEmpty()) return value;

        var length = value.length();
        if(length < 3) return value;

        if(length == 3) {
            //Mask CVV
            return String.valueOf('*').repeat(3);
        }

        if(length == 15 || length == 16) {
            //Mask PAN
            return String.valueOf('*').repeat(length - 4) + value.substring(length - 4, length);
        }

        return value;
    }
}
