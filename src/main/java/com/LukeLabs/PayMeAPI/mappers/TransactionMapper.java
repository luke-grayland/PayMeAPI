package com.LukeLabs.PayMeAPI.mappers;

import com.LukeLabs.PayMeAPI.constants.TransactionFileDefinition;
import com.LukeLabs.PayMeAPI.enums.TransactionTypes;
import com.LukeLabs.PayMeAPI.models.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionMapper {
    private final Logger logger = LoggerFactory.getLogger(TransactionMapper.class);

    public List<Transaction> MapTransactions(List<String> rawTransactionData) {
        List<Transaction> result = new ArrayList<>();
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmmss");

        rawTransactionData.forEach(line -> {
            var transaction = new Transaction();
            transaction.setTransactionId(line.substring(TransactionFileDefinition.TransactionId_Start, TransactionFileDefinition.TransactionId_End));

            var transactionTypeRaw = line.substring(TransactionFileDefinition.TransactionType_Start, TransactionFileDefinition.TransactionType_End)
                    .replace(" ", "");;
            String transactionType = "";

            try {
                var transactionTypeIndex = Integer.parseInt(transactionTypeRaw);
                transactionType = TransactionTypes.Values.get(transactionTypeIndex);
            } catch(Exception ex) {
                transactionType = "Unknown";
            }
            transaction.setTransactionType(transactionType);

            var amount = line
                    .substring(TransactionFileDefinition.Amount_Start, TransactionFileDefinition.Amount_End)
                    .replace(" ", "");
            try {
                transaction.setAmount(new BigDecimal(amount));
            } catch (NumberFormatException e) {
                logger.error("Error parsing transaction amount, defaulting to 0. {}", e.getMessage());
                transaction.setAmount(new BigDecimal(0));
            }

            var currencyCode = line
                    .substring(TransactionFileDefinition.Currency_Start, TransactionFileDefinition.Currency_End)
                    .replace(" ", "");
            transaction.setCurrency(currencyCode);

            var merchantName = line.substring(TransactionFileDefinition.MerchantName_Start, TransactionFileDefinition.MerchantName_End).trim();
            transaction.setMerchant(merchantName);

            var transactionDate = line.substring(TransactionFileDefinition.Date_Start, TransactionFileDefinition.Date_End);
            var transactionTime = line.substring(TransactionFileDefinition.Time_Start, TransactionFileDefinition.Time_End);
            var rawTimestamp = transactionDate + " " + transactionTime;

            LocalDateTime dateTime;
            try {
                dateTime = LocalDateTime.parse(rawTimestamp, formatter);
            } catch (Exception e) {
                logger.error("Error parsing transaction date, defaulting to now {}", e.getMessage());
                dateTime = LocalDateTime.now();
            }
            transaction.setTransactionsTimestamp(dateTime);

            result.add(transaction);
        });

        logger.info("Finished mapping {} transactions.", result.size());
        return result;
    }
}
