package com.LukeLabs.PayMeAPI.services;

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
    private static final double PER_DAY_TOTAL_SPEND_LIMIT = 1000;
    private static final double PER_DAY_SPEND_COUNT_LIMIT = 5;
    private static final List<String> SAFE_BET_CATEGORIES = List.of("egaming");

    public SpendProcessor(SpendRepository spendRepository, SpendMapper spendMapper) {
        this.spendRepository = spendRepository;
        this.spendMapper = spendMapper;
    }

    public Result<Boolean> logSpend(LogSpendRequest request) {
        var newSpend = new Spend.Builder(
                request.getCardId(),
                request.getSpendCategory(),
                request.getAmount(),
                request.getDatetime())
                .build();

        SpendDocument spendDocument = spendMapper.MapSpend(newSpend);
        spendRepository.save(spendDocument);

        boolean safeBetBlockRequired = false;

        if(SAFE_BET_CATEGORIES.contains(request.getSpendCategory())) {
            var fromDateTime = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
            List<SpendDocument> recentSpends = spendRepository
                    .findSpendByCardIdInLastDay(request.getCardId(), fromDateTime);

            if(recentSpends.isEmpty()) {
                logger.info("No recent spend data found - SafeBet block not required");
                return Result.success(true);
            }

            if(recentSpends.size() > PER_DAY_SPEND_COUNT_LIMIT) {
                logger.info("Recent spend count exceeds limit - SafeBet block required");
                safeBetBlockRequired = true;
            }

            if(!safeBetBlockRequired) {
                double spendTotal = 0;
                for (var spend : recentSpends) { spendTotal += spend.getAmount(); }

                if(spendTotal > PER_DAY_TOTAL_SPEND_LIMIT) {
                    logger.info("Recent spend total amount exceeds limit - SafeBet block required");
                    safeBetBlockRequired = true;
                }
            }

            if(safeBetBlockRequired) {
                //write block card functionality
            }
        }

        logger.info(String.format("Spend against card %s successfully stored", request.getCardId()));
        return Result.success(true);
    }
}
