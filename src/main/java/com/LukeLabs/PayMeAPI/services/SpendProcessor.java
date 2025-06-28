package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.constants.CardStatusConstants;
import com.LukeLabs.PayMeAPI.constants.SafeBetConstants;
import com.LukeLabs.PayMeAPI.handlers.*;
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
    private final static int twentyFourHoursInMilliSeconds = 24 * 60 * 60 * 1000;

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

        SpendDocument spendDocument = spendMapper.toSpendDocument(newSpend);
        spendRepository.save(spendDocument);

        if(!SafeBetConstants.Categories.All.contains(request.getSpendCategory())) {
            spendNotificationService.QueueNotification(request.getCardId(), "Spend successfully stored");
            return Result.success(String.format("Spend against card %s successfully stored", request.getCardId()));
        }

        var fromDateTime = new Date(System.currentTimeMillis() - twentyFourHoursInMilliSeconds);
        List<SpendDocument> recentSpends = spendRepository
                .findSpendByCardIdInLastDay(request.getCardId(), fromDateTime);

        if(recentSpends.isEmpty()) {
            logger.info("No recent spend data found - SafeBet block not required");
            return Result.success(String.format("Spend against card %s successfully stored", request.getCardId()));
        }

        //TODO: add configuration to allow for dependency injection
        var spendCountHandler = new SpendCountHandler();
        var spendLimitHandler = new SpendLimitHandler();
        var salaryFractionHandler = new SalaryFractionHandler();

        spendCountHandler.setNext(spendLimitHandler);
        spendLimitHandler.setNext(salaryFractionHandler);
        SafeBetResult safeBetResult = spendCountHandler.blockIsRequired(recentSpends);

        if(safeBetResult.blockIsRequired()) {
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

            String safeBetMessage = compileResponseMessage(request, safeBetResult.handler());
            return Result.success(safeBetMessage);
        }

        spendNotificationService.QueueNotification(request.getCardId(), "Spend successfully stored (SafeBet verified)");
        return Result.success(String.format("Spend against card %s successfully stored (SafeBet verified)", request.getCardId()));
    }

    private static String compileResponseMessage(LogSpendRequest request, SafeBetHandler handler) {
        StringBuilder message = new StringBuilder();
        message.append("Spend successfully stored. ");

        var handlerClass = handler.getClass();

        if (handlerClass.equals(SpendCountHandler.class)) {
            message.append(String.format("Daily Spend Count (%s) hit. ", SafeBetConstants.PER_DAY_SPEND_COUNT_LIMIT));
        }

        if (handlerClass.equals(SpendLimitHandler.class)) {
            message.append(String.format("Daily Total Spend Limit (%s) exceeded. ", SafeBetConstants.PER_DAY_TOTAL_SPEND_LIMIT));
        }

        if(handlerClass.equals(SalaryFractionHandler.class)) {
            message.append(String.format("Spend Amount exceeds Salary Fraction (%s). ", SafeBetConstants.SALARY_FRACTION));
        }

        message.append(String.format("SafeBet mode triggered for card %s. " +
                "Card has been blocked for future usage.", request.getCardId()));

        return message.toString();
    }
}
