package com.LukeLabs.PayMeAPI.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.extensions.CardExtensions;
import com.LukeLabs.PayMeAPI.models.Card;
import com.LukeLabs.PayMeAPI.models.requests.CreateCardRequest;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;

@Service
public class CardProcessor {
    private final CardRepository cardRepository;

    public CardProcessor(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card createCard(CreateCardRequest request) {
        var card = new Card();

        card.setID(UUID.randomUUID());
        card.setLimit(request.getControls().getLimit());
        card.setExpiryMonth(CardExtensions.MonthFromDateTime(request.getControls().getEndDate()));
        card.setExpiryYear(CardExtensions.YearFromDateTime(request.getControls().getEndDate()));
        
        //store card in db
        
        return card;
    }
}
