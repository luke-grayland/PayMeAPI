package com.LukeLabs.PayMeAPI.handlers;

import com.LukeLabs.PayMeAPI.constants.SafeBetConstants;
import com.LukeLabs.PayMeAPI.models.documents.SpendDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SpendCountHandler extends BaseSafeBetHandler {
    private final Logger logger = LoggerFactory.getLogger(SpendCountHandler.class);

    @Override
    protected SafeBetResult performCheck(List<SpendDocument> recentSpends) {

        if(recentSpends.size() >= SafeBetConstants.PER_DAY_SPEND_COUNT_LIMIT) {
            logger.info("Recent spend count exceeds limit - SafeBet block required");
            return new SafeBetResult(true, this);
        }

        return new SafeBetResult(false, this);
    }
}
