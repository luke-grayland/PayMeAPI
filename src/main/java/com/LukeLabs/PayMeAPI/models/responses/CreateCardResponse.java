package com.LukeLabs.PayMeAPI.models.responses;

import com.LukeLabs.PayMeAPI.models.DTOs.CardDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateCardResponse {
    @JsonProperty("card")
    private CardDTO card;
}
