package com.LukeLabs.PayMeAPI.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CardStatusUpdateRequest {
    @Schema(description = "Card Status", example = "ACTIVE")
    private String status;

    @Schema(description = "User ID", example = "505")
    private int userId;
}
