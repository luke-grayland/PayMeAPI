package com.LukeLabs.PayMeAPI.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationService {
    private static Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void QueueNotification(UUID cardId, String message) {
        logger.info(String.format("Notification queued for card %s: %s", cardId, message));
    }
}
