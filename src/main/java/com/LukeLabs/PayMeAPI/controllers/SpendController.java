package com.LukeLabs.PayMeAPI.controllers;

import com.LukeLabs.PayMeAPI.constants.SwaggerConstants;
import com.LukeLabs.PayMeAPI.models.requests.LogSpendRequest;
import com.LukeLabs.PayMeAPI.services.SpendProcessor;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/spend")
public class SpendController {
    private static final Logger logger = LoggerFactory.getLogger(SpendController.class);
    private final SpendProcessor logSpendProcessor;

    public SpendController(SpendProcessor logSpendProcessor) {
        this.logSpendProcessor = logSpendProcessor;
    }

    @Operation(summary = "Log spend", description = "Logs a spend against a card", tags = { SwaggerConstants.Tags.Spend })
    @PostMapping
    public ResponseEntity<String> logSpend(@RequestBody @Valid LogSpendRequest request) {
        try {
            var response = logSpendProcessor.logSpend(request);

            if(!response.isSuccess()) {
                return ResponseEntity.badRequest().body(response.getErrorMessage());
            }

            return ResponseEntity.ok(response.getData());

        } catch (Exception e) {
            logger.error(e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}
