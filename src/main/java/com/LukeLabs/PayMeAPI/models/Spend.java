package com.LukeLabs.PayMeAPI.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document("spend")
public class Spend {
    @Id
    private final UUID ID;
    private final UUID cardId;
    private final String spendCategory;
    private final double amount;

    public static class Builder {
        private final UUID ID;
        private final UUID cardId;
        private final String spendCategory;
        private final double amount;

        public Builder(UUID cardId, String spendCategory, double amount) {
            this.cardId = cardId;
            this.spendCategory = spendCategory;
            this.amount = amount;

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
    }
}
