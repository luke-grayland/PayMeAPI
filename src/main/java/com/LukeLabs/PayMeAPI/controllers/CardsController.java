package com.LukeLabs.PayMeAPI.controllers;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.LukeLabs.PayMeAPI.constants.SwaggerConstants;
import com.LukeLabs.PayMeAPI.models.DTOs.CardDTO;
import com.LukeLabs.PayMeAPI.utilities.config.errorHandling.BadRequestException;
import com.LukeLabs.PayMeAPI.utilities.config.errorHandling.CardNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
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

    public CardsController(CreateCardProcessor createCardProcessor, ViewCardProcessor viewCardProcessor,
                           UpdateCardProcessor updateCardProcessor) {
        this.createCardProcessor = createCardProcessor;
        this.viewCardProcessor = viewCardProcessor;
        this.updateCardProcessor = updateCardProcessor;
    }

    @GetMapping
    @Operation(summary = "Return cards by user", description = "Return all cards associated to a specified user",
            tags = { SwaggerConstants.Tags.Cards })
    public CompletableFuture<ResponseEntity<GetCardsByUserResponse>> getCardsByUser(@RequestParam("userID") int userID) {
        return viewCardProcessor.getCardsByUserID(userID)
            .thenApply(ResponseEntity::ok);
    }

    @PostMapping
    @Operation(summary = "Create a new card", description = "Create a new card for a specified user",
            tags = { SwaggerConstants.Tags.Cards })
    public ResponseEntity<CreateCardResponse> createCard(@RequestBody CreateCardRequest createCardRequest) {
        var result = createCardProcessor.createCard(createCardRequest);

        if(!result.isSuccess()) {
            throw new BadRequestException(result.getErrorMessage());
        }

        return ResponseEntity.ok(result.getData());
    }

    @PatchMapping("/{cardID}")
    @Operation(summary = "Update card status", description = "Update the status of a single card",
            tags = { SwaggerConstants.Tags.Cards })
    public CompletableFuture<ResponseEntity<String>> updateCardStatus(@PathVariable("cardID") UUID cardID,
            @RequestBody CardStatusUpdateRequest request) {

        return updateCardProcessor.updateCardStatus(cardID, request)
            .thenApply(result -> {
                if(!result.isSuccess()) {
                    throw new BadRequestException(result.getErrorMessage());
                }

                return ResponseEntity.ok().body(String.format("Successfully updated the card with ID %s", cardID));
            });
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "Get card details", description = "Get card by card ID",
            tags = { SwaggerConstants.Tags.Cards })
    public ResponseEntity<CardDTO> getCardDetails(@PathVariable("cardId") UUID cardID) {
        var result = viewCardProcessor.getCardByCardID(cardID);

        if(!result.isSuccess()) {
            throw new CardNotFoundException(result.getErrorMessage());
        }

        return ResponseEntity.ok(result.getData());
    }
}
