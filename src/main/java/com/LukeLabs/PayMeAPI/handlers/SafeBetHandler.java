package com.LukeLabs.PayMeAPI.handlers;

import com.LukeLabs.PayMeAPI.models.entities.SpendEntity;

import java.util.List;

public interface SafeBetHandler {
    void setNext(SafeBetHandler next);
    SafeBetResult blockIsRequired(List<SpendEntity> recentSpends);
}
