package com.LukeLabs.PayMeAPI.models;

import lombok.Data;

@Data
public class Result<T> {
    private T data;
    private Boolean success;
    private String errorMessage = "";
}
