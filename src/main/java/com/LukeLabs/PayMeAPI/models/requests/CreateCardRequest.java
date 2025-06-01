package com.LukeLabs.PayMeAPI.models.requests;

import com.LukeLabs.PayMeAPI.models.CardControls;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateCardRequest {
    @JsonProperty("userID")
    @Schema(description = "User's ID", example = "505")
    private int userID;
    
    @JsonProperty("label")
    @Schema(description = "Card Use Case Label", example = "London Business Trip Expenses")
    private String label;
    
    @JsonProperty("cardControls")
    private CardControls controls;
}