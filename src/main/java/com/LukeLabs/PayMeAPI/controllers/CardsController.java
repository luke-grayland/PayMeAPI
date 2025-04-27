package com.LukeLabs.PayMeAPI.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.LukeLabs.PayMeAPI.models.requests.CardStatusUpdateRequest;
import com.LukeLabs.PayMeAPI.models.requests.CreateCardRequest;
import com.LukeLabs.PayMeAPI.models.responses.CreateCardResponse;
import com.LukeLabs.PayMeAPI.models.responses.GetCardsByUserResponse;
import com.LukeLabs.PayMeAPI.services.CreateCardProcessor;
import com.LukeLabs.PayMeAPI.services.UpdateCardProcessor;
import com.LukeLabs.PayMeAPI.services.ViewCardProcessor;

@RestController
@RequestMapping("/api/cards")
public class CardsController {
    private final CreateCardProcessor createCardProcessor;
    private final ViewCardProcessor viewCardProcessor;
    private final UpdateCardProcessor updateCardProcessor;
    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);

    public CardsController(CreateCardProcessor createCardProcessor, 
        ViewCardProcessor viewCardProcessor, UpdateCardProcessor updateCardProcessor) {
        this.createCardProcessor = createCardProcessor;
        this.viewCardProcessor = viewCardProcessor;
        this.updateCardProcessor = updateCardProcessor;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<GetCardsByUserResponse>> getCardsByUser(@RequestParam("userID") int userID) {
        return viewCardProcessor.getCardsByUserID(userID)
            .thenApply(response -> { 
                return ResponseEntity.ok(response);
            })
            .exceptionally(ex -> {
                logger.error("Error getting cards by user ID", ex);
                return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
            });
    }

    @PostMapping
    public ResponseEntity<CreateCardResponse> createCard(@RequestBody CreateCardRequest createCardRequest) {
        try {
             var response = createCardProcessor.createCard(createCardRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }

    @PatchMapping("/{cardID}")
    public CompletableFuture<ResponseEntity<Object>> updateCardStatus(
            @PathVariable("cardID") UUID cardID, 
            @RequestBody CardStatusUpdateRequest request) {

        return updateCardProcessor.updateCardStatus(cardID, request.getStatus())
            .thenApply(result -> {
                if (result) {
                    return ResponseEntity.ok().build();
                }
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            })
            .exceptionally(ex -> {
                logger.error("Error updating card status for cardID: " + cardID, ex);
                return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
            });
    }
}
