package com.LukeLabs.PayMeAPI.models;

import lombok.Data;

@Data
public class Result<T> {
    private final T data;
    private final boolean isSuccess;
    private final String errorMessage;

    private Result(T data, boolean isSuccess, String errorMessage) {
        this.data = data;
        this.isSuccess = isSuccess;
        this.errorMessage = errorMessage;
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(data, true, null);
    }

    public static <T> Result<T> failure(String errorMessage) {
        return new Result<T>(null, false, errorMessage);
    }
}
