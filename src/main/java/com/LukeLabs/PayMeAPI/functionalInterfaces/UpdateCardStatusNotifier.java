package com.LukeLabs.PayMeAPI.functionalInterfaces;

import java.util.UUID;

public interface UpdateCardStatusNotifier {
    void queueNotification(UUID cardId, String status);
}
