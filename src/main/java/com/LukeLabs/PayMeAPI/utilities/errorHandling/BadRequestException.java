package com.LukeLabs.PayMeAPI.utilities.errorHandling;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
