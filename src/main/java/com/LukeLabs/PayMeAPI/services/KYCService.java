package com.LukeLabs.PayMeAPI.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.constants.KYCConstants;
import com.LukeLabs.PayMeAPI.models.Result;

@Service
public class KYCService {
    private static final List<String> BlockedKYCUsers = Arrays.asList("123", "456", "789");

    public Result<Integer> GetKYCStatus(UUID cardID) {

        if(BlockedKYCUsers.contains(cardID.toString())) {
            return Result.failure("User is KYC blocked");
        }

        return Result.success(KYCConstants.APPROVED);
    }
}
