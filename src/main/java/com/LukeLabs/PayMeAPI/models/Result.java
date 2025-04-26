package com.LukeLabs.PayMeAPI.models;

import lombok.Data;

@Data
public class Result<T> {
    private boolean success;
    private T data;
    private String errorMessage = "";
}
