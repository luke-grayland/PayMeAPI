package com.LukeLabs.PayMeAPI.models.requests;

import com.LukeLabs.PayMeAPI.models.CardControls;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateCardRequest {
    private String label;
    private int userID;
    
    @JsonProperty("cardControls")
    private CardControls controls;
}