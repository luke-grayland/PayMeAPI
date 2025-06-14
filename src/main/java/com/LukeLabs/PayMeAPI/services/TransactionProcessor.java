package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.constants.TransactionFileDefinition;
import com.LukeLabs.PayMeAPI.mappers.TransactionMapper;
import com.LukeLabs.PayMeAPI.models.Result;
import com.LukeLabs.PayMeAPI.models.Transaction;
import com.LukeLabs.PayMeAPI.models.responses.GetCardTransactionsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class TransactionProcessor {
    @Value("${transactionFilePath}")
    private String transactionFilePath;
    private final TransactionMapper transactionMapper;

    public TransactionProcessor(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    public Result<GetCardTransactionsResponse> getCardTransactions(String cardId) {
        var response = new GetCardTransactionsResponse();
        response.setCardId(cardId);

        List<String> fileLines = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(transactionFilePath))) {
            while(scanner.hasNextLine()) {
                fileLines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("Error reading transaction file: %s", e.getMessage()));
        }

        if(fileLines.isEmpty()) return Result.success(response);

        Optional<List<String>> matchingTransactions = GetMatchingTransactions(fileLines, cardId);
        if(matchingTransactions.isEmpty()) return Result.success(response);

        var mappedTransactions = transactionMapper.MapTransactions(matchingTransactions.get());
        SortTransactionByDateDesc(mappedTransactions);

        response.setTransactions(mappedTransactions);
        return Result.success(response);
    }

    private Optional<List<String>> GetMatchingTransactions(List<String> fileLines, String cardId) {
        var matchingTransactions = fileLines.stream().filter(line ->
                line.substring(TransactionFileDefinition.CardId_Start, TransactionFileDefinition.CardId_End)
                        .equals(cardId)).toList();

        if(matchingTransactions.isEmpty()) return Optional.empty();

        return Optional.of(matchingTransactions);
    }

    private static void SortTransactionByDateDesc(List<Transaction> transactions)
    {
        Comparator<Transaction> comparator = (Transaction o1, Transaction o2) -> {
            if(o1.getTransactionsTimestamp().isAfter(o2.getTransactionsTimestamp()))
                return 1;
            else if(o1.getTransactionsTimestamp().isEqual(o2.getTransactionsTimestamp()))
                return 0;
            else
                return -1;
        };

        transactions.sort(comparator);
    }
}
