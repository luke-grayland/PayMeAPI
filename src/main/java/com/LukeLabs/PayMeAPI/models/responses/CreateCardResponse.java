package com.LukeLabs.PayMeAPI.models.responses;

import com.LukeLabs.PayMeAPI.models.Card;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateCardResponse {
    @JsonProperty("card")
    private Card card;
}
