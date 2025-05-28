package com.LukeLabs.PayMeAPI.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class LogSpendRequest {
    @JsonProperty("cardId")
    private UUID cardId;

    @JsonProperty("spendCategory")
    private String spendCategory;

    @JsonProperty("amount")
    private double amount;

    @JsonProperty("dateTime")
    private LocalDateTime datetime;
}
