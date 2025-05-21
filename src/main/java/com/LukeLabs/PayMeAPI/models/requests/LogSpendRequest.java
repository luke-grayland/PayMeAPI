package com.LukeLabs.PayMeAPI.models.requests;

import lombok.Data;

import java.util.UUID;

@Data
public class LogSpendRequest {
    private UUID cardId;
    private String spendCategory;
    private double amount;
}
