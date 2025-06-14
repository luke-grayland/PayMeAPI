package com.LukeLabs.PayMeAPI.utilities.config.errorHandling;

public class CardNotFoundException extends RuntimeException{
    public CardNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
