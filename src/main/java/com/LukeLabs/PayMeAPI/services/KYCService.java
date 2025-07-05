package com.LukeLabs.PayMeAPI.services;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import lombok.experimental.PackagePrivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.constants.KYCStatus;
import com.LukeLabs.PayMeAPI.models.Result;

@Service
public class KYCService {
    private static final List<Integer> blockedKYCUsers = List.of(123, 456, 789);
    private final ConcurrentHashMap<Integer, Integer> previouslyCheckedUsers;
    private static final Logger logger = LoggerFactory.getLogger(KYCService.class);

    KYCService() {
        previouslyCheckedUsers = new ConcurrentHashMap<>();
    }

    @PackagePrivate
    KYCService(ConcurrentHashMap<Integer, Integer> customTestCache) {
        this.previouslyCheckedUsers = customTestCache;
    }

    public Result<Integer> GetKYCStatus(int userId) {

        Integer previouslyCheckedUserId = previouslyCheckedUsers.get(userId);
        if(previouslyCheckedUserId != null) {
            logger.info("User {} has previously been KYC approved", userId);
            return Result.success(previouslyCheckedUserId);
        }

        if(blockedKYCUsers.contains(userId)) {
            var errorMessage = String.format("User %s is KYC blocked", userId);
            logger.info(errorMessage);
            previouslyCheckedUsers.put(userId, KYCStatus.BLOCKED);
            return Result.failure(errorMessage);
        }

        logger.info("User {} is KYC approved, caching results", userId);
        previouslyCheckedUsers.put(userId, KYCStatus.APPROVED);
        return Result.success(KYCStatus.APPROVED);
    }
}
