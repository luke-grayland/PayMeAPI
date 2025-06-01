package com.LukeLabs.PayMeAPI.mappers;

import com.LukeLabs.PayMeAPI.models.Card;
import com.LukeLabs.PayMeAPI.models.ProvisionedCard;
import org.springframework.stereotype.Service;

@Service
public class CardMapper {

    public Card Map (ProvisionedCard provisionedCard) {
        Card card = new Card();

        card.setID(provisionedCard.getID());
        card.setCardNumber(provisionedCard.getCardNumber());
        card.setCvv(provisionedCard.getCvv());
        card.setExpiryMonth(provisionedCard.getExpiryMonth());
        card.setExpiryYear(provisionedCard.getExpiryYear());
        card.setLimit(provisionedCard.getLimit());
        card.setUserID(provisionedCard.getUserID());
        card.setStartDate(provisionedCard.getStartDate());
        card.setEndDate(provisionedCard.getEndDate());
        card.setStatus(provisionedCard.getStatus());
        card.setLabel(provisionedCard.getLabel());

        return card;
    }
}
