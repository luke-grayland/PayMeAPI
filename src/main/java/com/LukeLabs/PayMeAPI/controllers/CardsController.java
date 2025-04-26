package com.LukeLabs.PayMeAPI.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.LukeLabs.PayMeAPI.models.requests.CreateCardRequest;
import com.LukeLabs.PayMeAPI.models.responses.CreateCardResponse;
import com.LukeLabs.PayMeAPI.models.responses.GetCardsByUserResponse;
import com.LukeLabs.PayMeAPI.services.CreateCardProcessor;
import com.LukeLabs.PayMeAPI.services.ViewCardProcessor;

@RestController
@RequestMapping("/api/cards")
public class CardsController {
    private final CreateCardProcessor createCardProcessor;
    private final ViewCardProcessor viewCardProcessor;

    public CardsController(CreateCardProcessor createCardProcessor, ViewCardProcessor viewCardProcessor) {
        this.createCardProcessor = createCardProcessor;
        this.viewCardProcessor = viewCardProcessor;
    }

    @GetMapping
    public ResponseEntity<GetCardsByUserResponse> getCardsByUser(@RequestParam("userID") int userID) {
        try {
            var response = viewCardProcessor.getCardsByUserID(userID);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }

    @PostMapping
    public ResponseEntity<CreateCardResponse> createCard(@RequestBody CreateCardRequest createCardRequest) {
        try {
             var response = createCardProcessor.createCard(createCardRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
}
