package com.LukeLabs.PayMeAPI.utilities.errorHandling;

public class CardNotFoundException extends RuntimeException{
    public CardNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
