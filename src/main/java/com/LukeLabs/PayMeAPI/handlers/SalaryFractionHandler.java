package com.LukeLabs.PayMeAPI.handlers;

import com.LukeLabs.PayMeAPI.models.documents.SpendDocument;

import java.util.List;

public class SalaryFractionHandler extends BaseSafeBetHandler {
    @Override
    protected SafeBetResult performCheck(List<SpendDocument> recentSpends) {

        // find average salary from last 3 months using KYC profile
        // take spendTotal from recentSpends
        // if spendTotal exceeds SafeBetConstants.SALARY_FRACTION of your monthly salary, trigger SafeBet block

        return new SafeBetResult(false, this);
    }
}
