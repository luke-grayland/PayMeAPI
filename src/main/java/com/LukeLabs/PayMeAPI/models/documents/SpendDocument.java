package com.LukeLabs.PayMeAPI.models.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document("spend")
public class SpendDocument {
    @Id
    private UUID ID;
    private UUID cardId;
    private String spendCategory;
    private double amount;
}
