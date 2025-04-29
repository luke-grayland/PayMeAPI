package com.LukeLabs.PayMeAPI.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CardControls {
    @JsonProperty("limit")
    private double limit;

    @JsonProperty("startDate")
    private LocalDateTime startDate;

    @JsonProperty("endDate")
    private LocalDateTime endDate;

    @JsonProperty("authCountLimit")
    private int authCountLimit;
}