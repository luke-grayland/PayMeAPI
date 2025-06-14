package com.LukeLabs.PayMeAPI.controllers;

import com.LukeLabs.PayMeAPI.constants.SwaggerConstants;
import com.LukeLabs.PayMeAPI.models.Result;
import com.LukeLabs.PayMeAPI.models.responses.GetCardTransactionsResponse;
import com.LukeLabs.PayMeAPI.services.TransactionProcessor;
import com.LukeLabs.PayMeAPI.utilities.config.errorHandling.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {
    private final TransactionProcessor transactionProcessor;

    public TransactionController(TransactionProcessor transactionProcessor) {
        this.transactionProcessor = transactionProcessor;
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "Find transactions", description = "Find all file based transaction associated to a card",
            tags = { SwaggerConstants.Tags.Transactions })
    public ResponseEntity<GetCardTransactionsResponse> getTransactions(@PathVariable String cardId) {
        Result<GetCardTransactionsResponse> result = transactionProcessor.getCardTransactions(cardId);

        if(!result.isSuccess()) {
            throw new BadRequestException(result.getErrorMessage());
        }

        return ResponseEntity.status(HttpStatus.OK).body(result.getData());
    }
}
