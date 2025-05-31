package com.LukeLabs.PayMeAPI.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SpendNotificationService {
    private final NotificationService notificationService;

    public SpendNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    //The purpose of this class is to practice implementing composition + forwarding
    //rather than inheriting from the NotificationService

    public void QueueNotification(UUID cardId, String message) {
        notificationService.QueueNotification(cardId, message);
    }
}
