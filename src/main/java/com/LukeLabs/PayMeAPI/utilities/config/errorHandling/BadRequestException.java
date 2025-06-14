package com.LukeLabs.PayMeAPI.utilities.config.errorHandling;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }
}
