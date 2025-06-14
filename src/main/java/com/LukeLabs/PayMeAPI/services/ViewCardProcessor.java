package com.LukeLabs.PayMeAPI.services;

import java.util.Comparator;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.LukeLabs.PayMeAPI.mappers.CardMapper;
import com.LukeLabs.PayMeAPI.models.Card;
import com.LukeLabs.PayMeAPI.models.DTOs.CardDTO;
import com.LukeLabs.PayMeAPI.models.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.models.responses.GetCardsByUserResponse;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;

@Service
public class ViewCardProcessor {
    private final CardRepository cardRepository;
    private static final Logger logger = LoggerFactory.getLogger(ViewCardProcessor.class);
    private final CardMapper cardMapper;

    public ViewCardProcessor(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }
    
    @Async
    public CompletableFuture<GetCardsByUserResponse> getCardsByUserID(int userID) {
        logger.info("Finding cards associated to user: {}", userID);

        return cardRepository.findByUserIDAsync(userID)
            .thenApply(cards -> {
                logger.info("{} cards found", cards.size());

                var response = new GetCardsByUserResponse();
                var cardsByStartDate = cards.stream()
                        .sorted(Comparator.comparing(Card::getStartDate))
                        .map(cardMapper::toCardDTO).toList();
                response.setCards(cardsByStartDate);
                return response;
            });
    }

    public Result<CardDTO> getCardByCardID(UUID cardID) {
        logger.info("Finding card with ID: {}", cardID);
        var card = cardRepository.findById(cardID);

        return card.map(value -> Result.success(cardMapper.toCardDTO(value)))
                .orElseGet(() -> Result.failure("Card with ID " + cardID + " not found"));
    }
}
