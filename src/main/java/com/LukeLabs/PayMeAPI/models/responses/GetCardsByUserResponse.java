package com.LukeLabs.PayMeAPI.models.responses;

import java.util.List;

import com.LukeLabs.PayMeAPI.models.DTOs.CardDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetCardsByUserResponse {
    @JsonProperty("cards")
    private List<CardDTO> cards;
}