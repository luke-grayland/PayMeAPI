package com.LukeLabs.PayMeAPI.models.requests;

import com.LukeLabs.PayMeAPI.constants.SafeBetConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class LogSpendRequest {
    @JsonProperty("cardId")
    @Schema(description = "Card ID", example = "f34129d6-ab8b-455f-a24d-d5ad4997b165")
    private UUID cardId;

    @JsonProperty("spendCategory")
    @Schema(description = "Spend Category", example = SafeBetConstants.Categories.E_GAMING)
    private String spendCategory;

    @JsonProperty("amount")
    @Schema(description = "Spend Amount", example = "10.00")
    private double amount;

    @JsonProperty("dateTime")
    @Schema(description = "DateTime of Spend", example = "2025-06-01T08:00:00.000Z")
    private LocalDateTime datetime;
}
