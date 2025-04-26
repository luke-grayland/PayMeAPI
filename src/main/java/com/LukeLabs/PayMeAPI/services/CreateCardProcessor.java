package com.LukeLabs.PayMeAPI.services;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.controllers.CardsController;
import com.LukeLabs.PayMeAPI.extensions.CardExtensions;
import com.LukeLabs.PayMeAPI.models.Card;
import com.LukeLabs.PayMeAPI.models.requests.CreateCardRequest;
import com.LukeLabs.PayMeAPI.models.responses.CreateCardResponse;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;

@Service
public class CreateCardProcessor {
    private final CardRepository cardRepository;
    private final Random random;
    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);

    public CreateCardProcessor(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
        this.random = new Random();
    }

    public CreateCardResponse createCard(CreateCardRequest request) {
        logger.info(String.format("Creating new card for user: %s", request.getUserID()));
        var card = new Card();
        var expiryDate = LocalDateTime.now().plusYears(2);

        card.setID(UUID.randomUUID());
        card.setLimit(request.getControls().getLimit());
        card.setExpiryMonth(CardExtensions.MonthFromDateTime(expiryDate));
        card.setExpiryYear(CardExtensions.YearFromDateTime(expiryDate));
        card.setStartDate(request.getControls().getStartDate());
        card.setEndDate(request.getControls().getEndDate());
        card.setUserID(request.getUserID());
        card.setCardNumber(createCardNumber());
        card.setCvv(createCVV());

        cardRepository.save(card);
        logger.info(String.format("Card %s saved", card.getID()));
        
        var response = new CreateCardResponse();
        response.setCard(card);

        return response;
    }

    private String createCardNumber()
    {
        var cardNumber = new StringBuilder();
        
        for(var i=0; i<4; i++) {
            cardNumber.append(Integer.toString(random.nextInt(1000, 9999)));
        }
        
        return cardNumber.toString();
    }

    private String createCVV() {
        return Integer.toString(random.nextInt(100, 999));
    }
}
