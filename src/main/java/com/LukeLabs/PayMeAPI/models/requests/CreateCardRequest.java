package com.LukeLabs.PayMeAPI.models.requests;

import com.LukeLabs.PayMeAPI.models.CardControls;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateCardRequest {
    @JsonProperty("userID")
    private int userID;
    
    @JsonProperty("label")
    private String label;
    
    @JsonProperty("cardControls")
    private CardControls controls;
}