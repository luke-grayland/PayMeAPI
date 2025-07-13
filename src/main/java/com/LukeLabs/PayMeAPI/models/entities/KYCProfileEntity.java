package com.LukeLabs.PayMeAPI.models.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document("kycProfile")
public class KYCProfileEntity {
    @Id
    private UUID ID;
    private int userId;
    private double monthlyIncome;
}
