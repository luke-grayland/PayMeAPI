package com.LukeLabs.PayMeAPI.mappers;

import com.LukeLabs.PayMeAPI.models.Spend;
import com.LukeLabs.PayMeAPI.models.documents.SpendDocument;
import org.springframework.stereotype.Service;

@Service
public class SpendMapper {
    public SpendDocument MapSpend(Spend spend) {
        var result = new SpendDocument();
        result.setID(spend.getID());
        result.setAmount(spend.getAmount());
        result.setCardId(spend.getCardId());
        result.setSpendCategory(spend.getSpendCategory());
        return result;
    }
}
