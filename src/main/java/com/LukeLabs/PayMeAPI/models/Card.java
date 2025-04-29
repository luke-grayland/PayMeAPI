package com.LukeLabs.PayMeAPI.models;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import com.LukeLabs.PayMeAPI.constants.CardStatusConstants;
import com.LukeLabs.PayMeAPI.extensions.CardExtensions;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cards")
@Data
public class Card {
    @Id
    private final UUID ID;
    private final String cardNumber;
    private final String cvv;
    private final int expiryMonth;
    private final int expiryYear;
    private final double limit;
    private final int userID;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String status;
    private final String label;
    private int authCountLimit;

    public static class Builder {
        private final static Random random = new Random();
        private final LocalDateTime now = LocalDateTime.now();

        private final String cardNumber = createCardNumber();
        private final String cvv = createCVV();
        private final UUID ID = UUID.randomUUID();
        private final int expiryMonth = CardExtensions.MonthFromDateTime(now);
        private final int expiryYear = CardExtensions.YearFromDateTime(now);

        private final double limit;
        private final int userID;
        private final LocalDateTime startDate;
        private final LocalDateTime endDate;

        private String label = "";
        private String status = CardStatusConstants.INACTIVE;
        private int authCountLimit = 0;

        public Builder(double limit, int userID, LocalDateTime startDate, LocalDateTime endDate) {
            this.limit = limit;
            this.userID = userID;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public Builder label(String label) { this.label = label; return this; }

        public Builder authCountLimit(int authCountLimit) { this.authCountLimit = authCountLimit; return this; }

        public Builder status (String status) {
            if(!CardStatusConstants.all.contains(status)) {
                throw new IllegalArgumentException("Invalid card status: " + status);
            }
            this.status = status;
            return this;
        }

        public Card build() {
            return new Card(this);
        }

        private String createCardNumber()
        {
            var cardNumber = new StringBuilder();

            for(var i=0; i<4; i++) {
                cardNumber.append(Integer.toString(random.nextInt(1000, 9999)));
            }

            return cardNumber.toString();
        }

        private String createCVV() {
            return Integer.toString(random.nextInt(100, 999));
        }
    }

    private Card(Builder builder) {
        this.ID = builder.ID;
        this.cardNumber = builder.cardNumber;
        this.cvv = builder.cvv;
        this.expiryMonth = builder.expiryMonth;
        this.expiryYear = builder.expiryYear;
        this.limit = builder.limit;
        this.userID = builder.userID;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.status = builder.status;
        this.label = builder.label;
        this.authCountLimit = builder.authCountLimit;
    }
}
