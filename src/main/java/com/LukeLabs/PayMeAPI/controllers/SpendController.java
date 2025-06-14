package com.LukeLabs.PayMeAPI.controllers;

import com.LukeLabs.PayMeAPI.constants.SwaggerConstants;
import com.LukeLabs.PayMeAPI.models.requests.LogSpendRequest;
import com.LukeLabs.PayMeAPI.services.SpendProcessor;
import com.LukeLabs.PayMeAPI.utilities.config.errorHandling.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/spend")
public class SpendController {
    private final SpendProcessor logSpendProcessor;

    public SpendController(SpendProcessor logSpendProcessor) {
        this.logSpendProcessor = logSpendProcessor;
    }

    @PostMapping
    @Operation(summary = "Log spend", description = "Logs a spend against a card", tags = { SwaggerConstants.Tags.Spend })
    public ResponseEntity<String> logSpend(@RequestBody @Valid LogSpendRequest request) {
        var result = logSpendProcessor.logSpend(request);

        if(!result.isSuccess()) {
            throw new BadRequestException(result.getErrorMessage());
        }

        return ResponseEntity.ok(result.getData());
    }
}
