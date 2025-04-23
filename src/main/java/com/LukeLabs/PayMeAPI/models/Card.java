package com.LukeLabs.PayMeAPI.models;

import lombok.Data;

@Data
public class Card {
    private String cardNumber;
    private String cvv;
    private int expiryMonth;
    private int expiryYear;
    private double limit;
}
