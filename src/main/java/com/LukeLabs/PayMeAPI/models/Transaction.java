package com.LukeLabs.PayMeAPI.models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Transaction {
    private String transactionId;
    private String transactionType;
    private BigDecimal amount;
    private String currency;
    private String merchant;
    private LocalDateTime transactionsTimestamp;
}
