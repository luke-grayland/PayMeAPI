package com.LukeLabs.PayMeAPI.services;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.constants.KYCConstants;
import com.LukeLabs.PayMeAPI.controllers.CardsController;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;

@Service
public class UpdateCardProcessor {
    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);
    private final static List<Integer> validKYCCodes = List.of(KYCConstants.APPROVED, KYCConstants.PRE_APPROVED);
    private final KYCService kycService;
    private final CardRepository cardRepository;

    public UpdateCardProcessor(KYCService kycService, CardRepository cardRepository) {
        this.kycService = kycService;
        this.cardRepository = cardRepository;
    }

    @Async
    public CompletableFuture<Boolean> updateCardStatus(UUID cardID, String status) {
        
        var kycStatusResult = kycService.GetKYCStatus(cardID);

        if(!kycStatusResult.isSuccess()) {
            logger.error("Error fetching KYC status {}", kycStatusResult.getErrorMessage());
            return CompletableFuture.completedFuture(false);
        }

        if(!validKYCCodes.contains(kycStatusResult.getData()))
        {
            logger.error("Unable to update card status due to pending KYC approval");
            return CompletableFuture.completedFuture(false);
        }

        // cardRepository.updateCardStatus(cardID, status);

        return CompletableFuture.completedFuture(true);
    }
}
