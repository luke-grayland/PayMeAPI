package com.LukeLabs.PayMeAPI.models.responses;

import com.LukeLabs.PayMeAPI.models.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class GetCardTransactionsResponse {
    private String cardId;
    private List<Transaction> transactions;
}
