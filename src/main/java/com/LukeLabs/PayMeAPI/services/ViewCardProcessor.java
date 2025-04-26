package com.LukeLabs.PayMeAPI.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.controllers.CardsController;
import com.LukeLabs.PayMeAPI.models.responses.GetCardsByUserResponse;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;

@Service
public class ViewCardProcessor {
    private final CardRepository cardRepository;
    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);

    public ViewCardProcessor(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public GetCardsByUserResponse getCardsByUserID(int userID) {
        logger.info(String.format("Finding cards associated to user: %s", userID));
        var cards = cardRepository.findByUserID(userID);
        logger.info(String.format("%s cards found", cards.size()));

        var response = new GetCardsByUserResponse();
        response.setCards(cards);

        return response;
    }
}
