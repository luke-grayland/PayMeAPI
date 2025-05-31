package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.constants.CardStatusConstants;
import com.LukeLabs.PayMeAPI.constants.SafeBetConstants;
import com.LukeLabs.PayMeAPI.mappers.SpendMapper;
import com.LukeLabs.PayMeAPI.models.Result;
import com.LukeLabs.PayMeAPI.models.Spend;
import com.LukeLabs.PayMeAPI.models.documents.SpendDocument;
import com.LukeLabs.PayMeAPI.models.requests.LogSpendRequest;
import com.LukeLabs.PayMeAPI.repositories.SpendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpendProcessor {
    private static final Logger logger = LoggerFactory.getLogger(SpendProcessor.class);
    private final SpendRepository spendRepository;
    private final SpendMapper spendMapper;
    private final SpendNotificationService spendNotificationService;
    private final CardStatusUpdateService cardStatusUpdateService;
    private final BlockedCardCheckService blockedCardCheckService;

    public SpendProcessor(SpendRepository spendRepository, SpendMapper spendMapper,
                          SpendNotificationService spendNotificationService,
                          CardStatusUpdateService cardStatusUpdateService,
                          BlockedCardCheckService blockedCardCheckService) {
        this.spendRepository = spendRepository;
        this.spendMapper = spendMapper;
        this.spendNotificationService = spendNotificationService;
        this.cardStatusUpdateService = cardStatusUpdateService;
        this.blockedCardCheckService = blockedCardCheckService;
    }

    public Result<String> logSpend(LogSpendRequest request) {
        var blockedCardCheckResult = blockedCardCheckService.CheckByCardID(request.getCardId());
        if(!blockedCardCheckResult.isSuccess()) return blockedCardCheckResult;

        var newSpend = new Spend.Builder(
                request.getCardId(),
                request.getSpendCategory(),
                request.getAmount(),
                request.getDatetime())
                .build();

        SpendDocument spendDocument = spendMapper.MapSpend(newSpend);
        spendRepository.save(spendDocument);

        boolean spendCountExceeded = false;
        boolean spendLimitExceeded = false;

        if(SafeBetConstants.SAFE_BET_CATEGORIES.contains(request.getSpendCategory())) {
            var fromDateTime = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
            List<SpendDocument> recentSpends = spendRepository
                    .findSpendByCardIdInLastDay(request.getCardId(), fromDateTime);

            if(recentSpends.isEmpty()) {
                logger.info("No recent spend data found - SafeBet block not required");
                return Result.success(String.format("Spend against card %s successfully stored", request.getCardId()));
            }

            if(recentSpends.size() >= SafeBetConstants.PER_DAY_SPEND_COUNT_LIMIT) {
                logger.info("Recent spend count exceeds limit - SafeBet block required");
                spendCountExceeded = true;
            }

            if(!spendCountExceeded) {
                double spendTotal = 0;
                for (var spend : recentSpends) { spendTotal += spend.getAmount(); }

                if(spendTotal > SafeBetConstants.PER_DAY_TOTAL_SPEND_LIMIT) {
                    logger.info("Recent spend total amount exceeds limit - SafeBet block required");
                    spendLimitExceeded = true;
                }
            }

            if(spendCountExceeded || spendLimitExceeded) {
                Result<String> cardUpdateResult;
                try {
                    cardUpdateResult = cardStatusUpdateService
                            .UpdateStaus(request.getCardId(), CardStatusConstants.BLOCKED).get();
                } catch(Exception ex) {
                    logger.error(ex.getMessage());
                    return Result.failure(ex.getMessage());
                }

                if(!cardUpdateResult.isSuccess()) {
                    return Result.failure(cardUpdateResult.getErrorMessage());
                }

                String safeBetMessage = compileResponseMessage(request, spendCountExceeded, spendLimitExceeded);
                return Result.success(safeBetMessage);
            }
        }

        spendNotificationService.QueueNotification(request.getCardId(), "Spend successfully stored");
        return Result.success(String.format("Spend against card %s successfully stored", request.getCardId()));
    }

    private static String compileResponseMessage(LogSpendRequest request, boolean spendCountExceeded, boolean spendLimitExceeded) {
        StringBuilder message = new StringBuilder();
        message.append("Spend successfully stored. ");

        if (spendCountExceeded) {
            message.append(String.format("Daily Spend Count (%s) hit. ", SafeBetConstants.PER_DAY_SPEND_COUNT_LIMIT));
        }

        if (spendLimitExceeded) {
            message.append(String.format("Daily Total Spend Limit (%s) exceeded. ", SafeBetConstants.PER_DAY_TOTAL_SPEND_LIMIT));
        }

        message.append(String.format("SafeBet mode triggered for card %s. " +
                "Card has been blocked for future usage.", request.getCardId()));

        return message.toString();
    }
}
