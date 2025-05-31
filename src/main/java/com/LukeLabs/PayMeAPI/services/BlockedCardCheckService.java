package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.constants.CardStatusConstants;
import com.LukeLabs.PayMeAPI.models.Result;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlockedCardCheckService {
    private final CardRepository cardRepository;

    public BlockedCardCheckService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Result<String> CheckByCardID(UUID cardId) {
        var card = cardRepository.findById(cardId);

        if(card.isEmpty()) {
            return Result.failure(String.format("Card %s not found. Cannot store spend.", cardId));
        }

        if(card.get().getStatus().equals(CardStatusConstants.BLOCKED)) {
            return Result.failure(String.format("Card %s is blocked. Cannot store spend.", cardId));
        }

        return Result.success("Card is not blocked");
    }
}
