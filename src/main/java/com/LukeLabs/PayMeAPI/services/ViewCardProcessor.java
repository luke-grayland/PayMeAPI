package com.LukeLabs.PayMeAPI.services;

import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.models.responses.GetCardsByUserResponse;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;

@Service
public class ViewCardProcessor {
    private final CardRepository cardRepository;

    public ViewCardProcessor(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public GetCardsByUserResponse getCardsByUserID(int userID) {
        var cards = cardRepository.findByUserID(userID);

        var response = new GetCardsByUserResponse();
        response.setCards(cards);

        return response;
    }
}
