package com.LukeLabs.PayMeAPI.handlers;

import com.LukeLabs.PayMeAPI.constants.SafeBetConstants;
import com.LukeLabs.PayMeAPI.models.entities.KYCProfileEntity;
import com.LukeLabs.PayMeAPI.models.entities.SpendEntity;
import com.LukeLabs.PayMeAPI.repositories.CardRepository;
import com.LukeLabs.PayMeAPI.repositories.KYCProfileRepository;
import com.LukeLabs.PayMeAPI.utilities.errorHandling.CardNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SalaryFractionHandler extends BaseSafeBetHandler {
    private final KYCProfileRepository kycProfileRepository;
    private final CardRepository cardRepository;
    private static final Logger logger = LoggerFactory.getLogger(SalaryFractionHandler.class);

    public SalaryFractionHandler(KYCProfileRepository kycProfileRepository, CardRepository cardRepository) {
        this.kycProfileRepository = kycProfileRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public SafeBetResult performCheck(List<SpendEntity> recentSpends) {
        if(recentSpends.isEmpty()) {
            return new SafeBetResult(false, this);
        }

        UUID cardId = recentSpends.stream().findFirst().get().getCardId();

        int userID = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("Card associated to spend not found"))
                .getUserID();

        Optional<KYCProfileEntity> kycProfile = kycProfileRepository.findByUserId(userID);

        if(kycProfile.isEmpty()) {
            logger.info(String.format("No associated KYC profile found for user %s", userID));
            return new SafeBetResult(false, this);
        }

        double monthlyIncome = kycProfile.get().getMonthlyIncome();

        double totalRecentSpend = 0;
        for(SpendEntity spend : recentSpends) {
            totalRecentSpend += spend.getAmount();
        }

        if((monthlyIncome * SafeBetConstants.SALARY_FRACTION) < totalRecentSpend ) {
            logger.info(String.format("Salary fraction %s exceeded", SafeBetConstants.SALARY_FRACTION));
            return new SafeBetResult(true, this);
        }

        logger.info("Salary fraction not exceeded, SafeBet condition verified as safe");
        return new SafeBetResult(false, this);
    }
}
