package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.functionalInterfaces.UpdateCardStatusNotifier;
import com.LukeLabs.PayMeAPI.models.Card;
import com.LukeLabs.PayMeAPI.models.Result;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class CardStatusUpdateService {
    private static final Logger logger = LoggerFactory.getLogger(CardStatusUpdateService.class);
    private final CardRepository cardRepository;
    private final NotificationService notificationService;

    public CardStatusUpdateService(CardRepository cardRepository, NotificationService notificationService) {
        this.cardRepository = cardRepository;
        this.notificationService = notificationService;
    }

    public CompletableFuture<Result<String>> UpdateStaus(UUID cardID, String staus) {

        UpdateCardStatusNotifier notifier = (UUID cardRef, String status) -> {
            notificationService.QueueNotification(cardRef, "Updated to " + status);
        };

        Optional<Card> card = cardRepository.findById(cardID);
        if(card.isEmpty()) {
            return CompletableFuture.completedFuture(Result.failure("Card not found"));
        }

        card.get().setStatus(staus);
        cardRepository.save(card.get());
        logger.info("Card updated successfully");

        notifier.queueNotification(cardID, staus);

        return CompletableFuture.completedFuture(Result.success("Card status successfully updated"));
    }
}
