package com.LukeLabs.PayMeAPI.services;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.constants.KYCConstants;
import com.LukeLabs.PayMeAPI.models.Result;

@Service
public class KYCService {
    
    public CompletableFuture<Result<Integer>> GetKYCStatus(UUID cardID) {
        var futureResult = new CompletableFuture<Result<Integer>>();

        // Mocking an async task for the sake of practising async in calling code
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Thread.sleep(2000);

                var result = new Result<Integer>();
                result.setData(KYCConstants.APPROVED);
                result.setSuccess(true);

                futureResult.complete(result);
            } catch (InterruptedException ex) {
                var result = new Result<Integer>();
                result.setSuccess(false);
                result.setErrorMessage(ex.getMessage());
                futureResult.complete(result);
            }
        });

        return futureResult;
    }
}
