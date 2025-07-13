package com.LukeLabs.PayMeAPI.handlers;

import com.LukeLabs.PayMeAPI.constants.SafeBetConstants;
import com.LukeLabs.PayMeAPI.models.entities.SpendEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SpendLimitHandler extends BaseSafeBetHandler {
    private final Logger logger = LoggerFactory.getLogger(SpendLimitHandler.class);

    @Override
    protected SafeBetResult performCheck(List<SpendEntity> recentSpends) {

        double spendTotal = 0;
        for (var spend : recentSpends) {
            spendTotal += spend.getAmount();
        }

        if(spendTotal > SafeBetConstants.PER_DAY_TOTAL_SPEND_LIMIT) {
            logger.info("Recent spend total amount exceeds limit - SafeBet block required");
            return new SafeBetResult(true, this);
        }

        return new SafeBetResult(false, this);
    }
}
