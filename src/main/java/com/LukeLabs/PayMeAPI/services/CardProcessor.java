package com.LukeLabs.PayMeAPI.services;

import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.extensions.CardExtensions;
import com.LukeLabs.PayMeAPI.models.Card;
import com.LukeLabs.PayMeAPI.models.requests.CreateCardRequest;

@Service
public class CardProcessor {
    public Card createCard(CreateCardRequest request) {
        var card = new Card();

        card.setLimit(request.getControls().getLimit());
        card.setExpiryMonth(CardExtensions.MonthFromDateTime(request.getControls().getEndDate()));
        card.setExpiryYear(CardExtensions.YearFromDateTime(request.getControls().getEndDate()));
        
        return card;
    }
}
