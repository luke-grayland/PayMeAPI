package com.LukeLabs.PayMeAPI.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.LukeLabs.PayMeAPI.constants.SwaggerConstants;
import com.LukeLabs.PayMeAPI.models.Card;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Return cards by user",
            description = "Return all cards associated to a specified user",
            tags = { SwaggerConstants.Tags.Cards })
    @GetMapping
    public CompletableFuture<ResponseEntity<GetCardsByUserResponse>> getCardsByUser(@RequestParam("userID") int userID) {
        return viewCardProcessor.getCardsByUserID(userID)
            .thenApply(ResponseEntity::ok)
            .exceptionally(ex -> {
                logger.error("Error getting cards by user ID", ex);
                return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
            });
    }

    @Operation(
            summary = "Create a new card",
            description = "Create a new card for a specified user",
            tags = { SwaggerConstants.Tags.Cards })
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

    @Operation(
            summary = "Update card status",
            description = "Update the status of a single card",
            tags = { SwaggerConstants.Tags.Cards })
    @PatchMapping("/{cardID}")
    public CompletableFuture<ResponseEntity<String>> updateCardStatus(
            @PathVariable("cardID") UUID cardID, 
            @RequestBody CardStatusUpdateRequest request) {

        return updateCardProcessor.updateCardStatus(cardID, request)
            .thenApply(result -> {
                if (result.isSuccess()) {
                    var message = String.format("Successfully updated the card with ID %s", cardID);
                    logger.info(message);
                    return ResponseEntity.ok().body(message);
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(result.getErrorMessage());
            })
            .exceptionally(ex -> {
                var errorMessage = "Error updating card status for cardID: " + cardID;
                logger.error(errorMessage, ex);
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(errorMessage);
            });
    }

    @Operation(
            summary = "Get card details",
            description = "Get card by card ID",
            tags = { SwaggerConstants.Tags.Cards })
    @GetMapping("/{cardId}")
    public ResponseEntity<Card> getCardDetails(@PathVariable("cardId") UUID cardID) {
        try {
            var result = viewCardProcessor.getCardByCardID(cardID);

            if(!result.isSuccess()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            return ResponseEntity.ok(result.getData());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
