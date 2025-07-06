package com.LukeLabs.PayMeAPI.services;

import java.util.List;

import com.LukeLabs.PayMeAPI.utilities.cache.LimitedCache;
import lombok.experimental.PackagePrivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.constants.KYCStatus;
import com.LukeLabs.PayMeAPI.models.Result;

@Service
public class KYCService {
    private static final List<Integer> blockedKYCUsers = List.of(123, 456, 789);
    private final LimitedCache<Integer, Integer> nonBlockedUsersCache;
    private static final Logger logger = LoggerFactory.getLogger(KYCService.class);

    KYCService() {
        nonBlockedUsersCache = new LimitedCache<>(1000);
    }

    @PackagePrivate
    KYCService(LimitedCache<Integer, Integer> customTestCache) {
        this.nonBlockedUsersCache = customTestCache;
    }

    public Result<Integer> GetKYCStatus(int userId) {

        Integer previouslyCheckedUserId = nonBlockedUsersCache.get(userId);
        if(previouslyCheckedUserId != null) {
            logger.info("User {} has previously been KYC approved", userId);
            return Result.success(previouslyCheckedUserId);
        }

        if(blockedKYCUsers.contains(userId)) {
            var errorMessage = String.format("User %s is KYC blocked", userId);
            logger.info(errorMessage);
            nonBlockedUsersCache.put(userId, KYCStatus.BLOCKED);
            return Result.failure(errorMessage);
        }

        logger.info("User {} is KYC approved, caching results", userId);
        nonBlockedUsersCache.put(userId, KYCStatus.APPROVED);
        return Result.success(KYCStatus.APPROVED);
    }
}
