package com.LukeLabs.PayMeAPI.functionalInterfaces;

import java.util.UUID;

@FunctionalInterface
public interface NewCardNotifier {
    void queueNotification(int userId, UUID cardId);
}
