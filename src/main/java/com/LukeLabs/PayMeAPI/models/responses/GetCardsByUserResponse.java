package com.LukeLabs.PayMeAPI.models.responses;

import java.util.List;

import com.LukeLabs.PayMeAPI.models.Card;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetCardsByUserResponse {
    @JsonProperty("cards")
    private List<Card> cards;
}