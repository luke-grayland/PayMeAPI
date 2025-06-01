package com.LukeLabs.PayMeAPI.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CardControls {
    @JsonProperty("limit")
    @Schema(description = "Card Limit", example = "20.00")
    private double limit;

    @JsonProperty("startDate")
    @Schema(description = "Card Start Date", example = "2025-06-01T15:00:00.000Z")
    private LocalDateTime startDate;

    @JsonProperty("endDate")
    @Schema(description = "Card End Date", example = "2027-09-01T00:00:00.000Z")
    private LocalDateTime endDate;
}