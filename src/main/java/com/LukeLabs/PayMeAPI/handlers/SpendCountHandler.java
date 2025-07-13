package com.LukeLabs.PayMeAPI.handlers;

import com.LukeLabs.PayMeAPI.constants.SafeBetConstants;
import com.LukeLabs.PayMeAPI.models.entities.SpendEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpendCountHandler extends BaseSafeBetHandler {
    private final Logger logger = LoggerFactory.getLogger(SpendCountHandler.class);

    @Override
    protected SafeBetResult performCheck(List<SpendEntity> recentSpends) {

        if(recentSpends.size() >= SafeBetConstants.PER_DAY_SPEND_COUNT_LIMIT) {
            logger.info("Recent spend count exceeds limit - SafeBet block required");
            return new SafeBetResult(true, this);
        }

        return new SafeBetResult(false, this);
    }
}
