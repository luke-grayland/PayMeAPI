package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.constants.TransactionFileDefinition;
import com.LukeLabs.PayMeAPI.mappers.TransactionMapper;
import com.LukeLabs.PayMeAPI.models.responses.GetCardTransactionsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class TransactionProcessor {
    @Value("${transactionFilePath}")
    private String transactionFilePath;
    private final TransactionMapper transactionMapper;

    public TransactionProcessor(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    public GetCardTransactionsResponse getCardTransactions(String cardId) {
        var response = new GetCardTransactionsResponse();
        response.setCardId(cardId);

        List<String> fileLines = new ArrayList<String>();
        try(Scanner scanner = new Scanner(new File(transactionFilePath))) {
            while(scanner.hasNextLine()) {
                fileLines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("Error reading transaction file: %s", e.getMessage()));
        }

        if(fileLines.isEmpty()) return response;

        var matchingTransactions = fileLines.stream().filter(line ->
                line.substring(TransactionFileDefinition.CardId_Start, TransactionFileDefinition.CardId_End)
                        .equals(cardId)).toList();

        if(matchingTransactions.isEmpty()) return response;

        var mappedTransactions = transactionMapper.MapTransactions(matchingTransactions);
        response.setTransactions(mappedTransactions);

        return response;
    }
}
