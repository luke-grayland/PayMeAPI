package com.LukeLabs.PayMeAPI.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.LukeLabs.PayMeAPI.models.Card;
import com.LukeLabs.PayMeAPI.models.requests.CreateCardRequest;
import com.LukeLabs.PayMeAPI.services.CardProcessor;

@RestController
@RequestMapping("/api/cards")
public class CardsController {
    private final CardProcessor cardProcessor;

    public CardsController(CardProcessor cardProcessor) {
        this.cardProcessor = cardProcessor;
    }

    @GetMapping
    public void getAllCards() {
    }

    @PostMapping
    public ResponseEntity<Card> postMethodName(@RequestBody CreateCardRequest createCardRequest) {
        try {
            var card = cardProcessor.createCard(createCardRequest);
            return ResponseEntity.ok(card);
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
        }
    }
}
