package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.constants.CardStatusConstants;
import com.LukeLabs.PayMeAPI.mappers.CardMapper;
import com.LukeLabs.PayMeAPI.models.ProvisionedCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.models.requests.CreateCardRequest;
import com.LukeLabs.PayMeAPI.models.responses.CreateCardResponse;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;

@Service
public class CreateCardProcessor {
    private final CardRepository cardRepository;
    private static final Logger logger = LoggerFactory.getLogger(CreateCardProcessor.class);
    private final CardMapper cardMapper;

    public CreateCardProcessor(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    public CreateCardResponse createCard(CreateCardRequest request) {

        logger.info("Creating new card for user: {}", request.getUserID());
        var provisionedCard = new ProvisionedCard.Builder(
                request.getControls().getLimit(),
                request.getUserID(),
                request.getControls().getStartDate(),
                request.getControls().getEndDate())
                .label(request.getLabel())
                .status(CardStatusConstants.ACTIVE)
                .authCountLimit(request.getControls().getAuthCountLimit())
                .build();

        var card = cardMapper.Map(provisionedCard);
        cardRepository.save(card);
        logger.info("Card saved: {}", card);

        var response = new CreateCardResponse();
        response.setCard(card);

        return response;
    }
}
