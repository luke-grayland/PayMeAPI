package com.LukeLabs.PayMeAPI.models;

import java.time.LocalDateTime;
import java.util.UUID;

import com.LukeLabs.PayMeAPI.constants.CardStatusConstants;
import com.LukeLabs.PayMeAPI.extensions.CardExtensions;
import com.LukeLabs.PayMeAPI.services.CardNumberGenerator;
import com.LukeLabs.PayMeAPI.utilities.errorHandling.InvalidCardStatusException;
import lombok.Data;

@Data
public class ProvisionedCard {
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

    public static class Builder {
        private final LocalDateTime now = LocalDateTime.now();

        private final String cardNumber = CardNumberGenerator.INSTANCE.createCardNumber();
        private final String cvv = CardNumberGenerator.INSTANCE.createCVV();
        private final UUID ID = UUID.randomUUID();
        private final int expiryMonth = CardExtensions.MonthFromDateTime(now);
        private final int expiryYear = CardExtensions.YearFromDateTime(now);

        private final double limit;
        private final int userID;
        private final LocalDateTime startDate;
        private final LocalDateTime endDate;

        private String label = "";
        private String status = CardStatusConstants.INACTIVE;

        public Builder(double limit, int userID, LocalDateTime startDate, LocalDateTime endDate) {
            this.limit = limit;
            this.userID = userID;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public Builder label(String label) { this.label = label; return this; }

        public Builder status (String status) {
            if(!CardStatusConstants.all.contains(status)) {
                throw new InvalidCardStatusException("Invalid card status: " + status);
            }
            this.status = status;
            return this;
        }

        public ProvisionedCard build() {
            return new ProvisionedCard(this);
        }
    }

    private ProvisionedCard(Builder builder) {
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
    }
}
