package com.LukeLabs.PayMeAPI.handlers;

import com.LukeLabs.PayMeAPI.models.entities.SpendEntity;

import java.util.List;

@SuppressWarnings("LombokSetterMayBeUsed")
public abstract class BaseSafeBetHandler implements SafeBetHandler{
    private SafeBetHandler next;

    public void setNext(SafeBetHandler next) {
        this.next = next;
    }

    protected abstract SafeBetResult performCheck(List<SpendEntity> recentSpends);

    public SafeBetResult blockIsRequired(List<SpendEntity> recentSpends) {
        SafeBetResult result = performCheck(recentSpends);

        if(result.blockIsRequired()) {
            return new SafeBetResult(true, this);
        }

        if(next == null) {
            return new SafeBetResult(false, this);
        }

        return next.blockIsRequired(recentSpends);
    }
}
