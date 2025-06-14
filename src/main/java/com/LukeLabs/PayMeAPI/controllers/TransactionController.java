package com.LukeLabs.PayMeAPI.controllers;

import com.LukeLabs.PayMeAPI.constants.SwaggerConstants;
import com.LukeLabs.PayMeAPI.models.responses.GetCardTransactionsResponse;
import com.LukeLabs.PayMeAPI.services.TransactionProcessor;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {
    private final TransactionProcessor transactionProcessor;
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    public TransactionController(TransactionProcessor transactionProcessor) {
        this.transactionProcessor = transactionProcessor;
    }

    @Operation(summary = "Find transactions", description = "Find all file based transaction associated to a card",
            tags = { SwaggerConstants.Tags.Transactions })
    @GetMapping("/{cardId}")
    public ResponseEntity<GetCardTransactionsResponse> getTransactions(@PathVariable String cardId) {
        try {
            var result = transactionProcessor.getCardTransactions(cardId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
