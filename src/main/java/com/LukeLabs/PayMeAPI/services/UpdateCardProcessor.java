package com.LukeLabs.PayMeAPI.services;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.LukeLabs.PayMeAPI.constants.CardStatusConstants;
import com.LukeLabs.PayMeAPI.models.Result;
import com.LukeLabs.PayMeAPI.models.requests.CardStatusUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.constants.KYCStatus;

@Service
public class UpdateCardProcessor {
    private static final Logger logger = LoggerFactory.getLogger(UpdateCardProcessor.class);
    private final static List<Integer> validKYCCodes = List.of(KYCStatus.APPROVED, KYCStatus.PRE_APPROVED);
    private final KYCService kycService;
    private final CardStatusUpdateService cardStatusUpdateService;

    public UpdateCardProcessor(KYCService kycService, CardStatusUpdateService cardStatusUpdateService) {
        this.kycService = kycService;
        this.cardStatusUpdateService = cardStatusUpdateService;
    }

    @Async
    public CompletableFuture<Result<String>> updateCardStatus(UUID cardID, CardStatusUpdateRequest request) {

        if(!CardStatusConstants.all.contains(request.getStatus())) {
            return CompletableFuture.completedFuture(Result.failure("Invalid card status"));
        }

        var kycStatusResult = kycService.GetKYCStatus(request.getUserId());

        if(!kycStatusResult.isSuccess()) {
            var errorMessage = String.format("Error fetching KYC status %s", kycStatusResult.getErrorMessage());
            logger.error(errorMessage);
            return CompletableFuture.completedFuture(Result.failure(errorMessage));
        }

        if(!validKYCCodes.contains(kycStatusResult.getData()))
        {
            var errorMessage = "Unable to update card status due to pending KYC approval";
            logger.error(errorMessage);
            return CompletableFuture.completedFuture(Result.failure(errorMessage));
        }

        return cardStatusUpdateService.UpdateStaus(cardID, request.getStatus());
    }
}
