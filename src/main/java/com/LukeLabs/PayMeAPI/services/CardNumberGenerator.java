package com.LukeLabs.PayMeAPI.services;

import java.util.Random;

public class CardNumberGenerator {
    public static final CardNumberGenerator INSTANCE = new CardNumberGenerator();
    private final static Random random = new Random();

    private CardNumberGenerator() {}

    public String createCardNumber()
    {
        var cardNumber = new StringBuilder();

        for(var i=0; i<4; i++) {
            cardNumber.append(Integer.toString(random.nextInt(1000, 9999)));
        }

        return cardNumber.toString();
    }

    public String createCVV() {
        return Integer.toString(random.nextInt(100, 999));
    }
}
