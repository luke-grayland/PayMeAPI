package com.LukeLabs.PayMeAPI.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Spend {
    private final UUID ID;
    private final UUID cardId;
    private final String spendCategory;
    private final double amount;
    private final LocalDateTime dateTime;

    public static class Builder {
        private final UUID ID;
        private final UUID cardId;
        private final String spendCategory;
        private final double amount;
        private final LocalDateTime dateTime;

        public Builder(UUID cardId, String spendCategory, double amount, LocalDateTime dateTime) {
            this.cardId = cardId;
            this.spendCategory = spendCategory;
            this.amount = amount;
            this.dateTime = dateTime;

            this.ID = UUID.randomUUID();
        }

        public Spend build() {
            return new Spend(this);
        }
    }

    private Spend(Builder builder) {
        this.cardId = builder.cardId;
        this.spendCategory = builder.spendCategory;
        this.amount = builder.amount;
        this.ID = builder.ID;
        this.dateTime = builder.dateTime;
    }
}
