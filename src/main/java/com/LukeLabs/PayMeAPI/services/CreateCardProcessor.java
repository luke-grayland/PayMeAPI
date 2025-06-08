package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.constants.CardStatusConstants;
import com.LukeLabs.PayMeAPI.functionalInterfaces.NewCardNotifier;
import com.LukeLabs.PayMeAPI.mappers.CardMapper;
import com.LukeLabs.PayMeAPI.models.ProvisionedCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.models.requests.CreateCardRequest;
import com.LukeLabs.PayMeAPI.models.responses.CreateCardResponse;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;

import java.util.UUID;

@Service
public class CreateCardProcessor {
    private final CardRepository cardRepository;
    private static final Logger logger = LoggerFactory.getLogger(CreateCardProcessor.class);
    private final CardMapper cardMapper;
    private final NotificationService notificationService;

    public CreateCardProcessor(CardRepository cardRepository, CardMapper cardMapper, NotificationService notificationService) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
        this.notificationService = notificationService;
    }

    public CreateCardResponse createCard(CreateCardRequest request) {

        NewCardNotifier notifier = (int userId, UUID cardId) ->
                notificationService.QueueNotification(cardId, "Card created for user " + userId);

        logger.info("Creating new card for user: {}", request.getUserID());
        var provisionedCard = new ProvisionedCard.Builder(
                request.getControls().getLimit(),
                request.getUserID(),
                request.getControls().getStartDate(),
                request.getControls().getEndDate())
                .label(request.getLabel())
                .status(CardStatusConstants.ACTIVE)
                .build();

        var card = cardMapper.toCard(provisionedCard);
        cardRepository.save(card);
        logger.info("Card saved: {}", card);

        notifier.queueNotification(request.getUserID(), card.getID());

        var response = new CreateCardResponse();
        response.setCard(card);

        return response;
    }
}
