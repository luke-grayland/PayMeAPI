package com.LukeLabs.PayMeAPI.models;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CardControls {
    private double limit;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}