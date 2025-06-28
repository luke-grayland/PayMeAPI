package com.LukeLabs.PayMeAPI.handlers;

import com.LukeLabs.PayMeAPI.models.documents.SpendDocument;

import java.util.List;

public interface SafeBetHandler {
    void setNext(SafeBetHandler next);
    SafeBetResult blockIsRequired(List<SpendDocument> recentSpends);
}
