package com.LukeLabs.PayMeAPI.services;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.constants.KYCStatus;
import com.LukeLabs.PayMeAPI.models.Result;

@Service
public class KYCService {
    private static final List<Integer> BlockedKYCUsers = List.of(123, 456, 789);
    private static final HashMap<Integer, Integer> PreviouslyCheckedUsers = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(KYCService.class);

    public Result<Integer> GetKYCStatus(int userId) {

        var previouslyCheck = PreviouslyCheckedUsers.get(userId);
        if(previouslyCheck != null) {
            logger.info("User {} has previously been KYC approved", userId);
            return Result.success(previouslyCheck);
        }

        if(BlockedKYCUsers.contains(userId)) {
            var errorMessage = String.format("User %s is KYC blocked", userId);
            logger.info(errorMessage);
            PreviouslyCheckedUsers.put(userId, KYCStatus.BLOCKED);
            return Result.failure(errorMessage);
        }

        logger.info("User {} is KYC approved, caching results", userId);
        PreviouslyCheckedUsers.put(userId, KYCStatus.APPROVED);
        return Result.success(KYCStatus.APPROVED);
    }
}
