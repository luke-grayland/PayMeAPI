package com.LukeLabs.PayMeAPI.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.LukeLabs.PayMeAPI.controllers.CardsController;
import com.LukeLabs.PayMeAPI.models.Result;

@Service
public class UpdateCardProcessor {
    private static final Logger logger = LoggerFactory.getLogger(CardsController.class);
    private final static List<Integer> validKYCCodes = List.of(4, 8, 9);

    public boolean updateCardStatus(int cardID, String status) {
        
        var result = new Result<Integer>();
        
        //async

        if(!result.getSuccess())
        {
            logger.error(String.format("Error fetching KYC status %s", result.getErrorMessage()));
            return false;
        }

        if(!validKYCCodes.contains(result.getData()))
        {
            logger.error("Unable to update card status due to pending KYC approval");
            return false;
        }

        //repository, update card status
        
        return true;
    }
}
