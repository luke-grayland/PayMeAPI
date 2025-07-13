package com.LukeLabs.PayMeAPI.models.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document("spend")
public class SpendEntity {
    @Id
    private UUID ID;
    private UUID cardId;
    private String spendCategory;
    private double amount;
    private LocalDateTime dateTime;
}
