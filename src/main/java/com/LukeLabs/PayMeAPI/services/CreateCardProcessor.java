package com.LukeLabs.PayMeAPI.services;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.constants.CardStatusConstants;
import com.LukeLabs.PayMeAPI.extensions.CardExtensions;
import com.LukeLabs.PayMeAPI.models.Card;
import com.LukeLabs.PayMeAPI.models.requests.CreateCardRequest;
import com.LukeLabs.PayMeAPI.models.responses.CreateCardResponse;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;

@Service
public class CreateCardProcessor {
    private final CardRepository cardRepository;
    private static final Logger logger = LoggerFactory.getLogger(CreateCardProcessor.class);

    public CreateCardProcessor(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CreateCardResponse createCard(CreateCardRequest request) {
        logger.info("Creating new card for user: {}", request.getUserID());
        var card = new Card.Builder(
                request.getControls().getLimit(),
                request.getUserID(),
                request.getControls().getStartDate(),
                request.getControls().getEndDate())
                .label(request.getLabel())
                .status(CardStatusConstants.ACTIVE)
                .authCountLimit(request.getControls().getAuthCountLimit())
                .build();

        cardRepository.save(card);
        logger.info("Card {} saved", card.getID());
        
        var response = new CreateCardResponse();
        response.setCard(card);

        return response;
    }
}
