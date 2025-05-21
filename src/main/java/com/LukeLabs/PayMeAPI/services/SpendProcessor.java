package com.LukeLabs.PayMeAPI.services;

import com.LukeLabs.PayMeAPI.models.Spend;
import com.LukeLabs.PayMeAPI.models.requests.LogSpendRequest;
import com.LukeLabs.PayMeAPI.repositories.SpendRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpendProcessor {
    private final SpendRepository spendRepository;

    public SpendProcessor(SpendRepository spendRepository) {
        this.spendRepository = spendRepository;
    }

    public Boolean logSpend(LogSpendRequest request) {

        var newSpend = new Spend.Builder(
                request.getCardId(),
                request.getSpendCategory(),
                request.getAmount())
                .build();

        spendRepository.save(newSpend);

        //Trigger SafeBet functionality
        if(request.getSpendCategory().equals("egaming")) {
            List<Spend> allCardSpend = spendRepository.findSpendByCardId(request.getCardId());
            //find all spend associated to card
            //if total spend is above x amount, or if spend frequency exceeds threshold, set card to block
        }

        return true;
    }
}
