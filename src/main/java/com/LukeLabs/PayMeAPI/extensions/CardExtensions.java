package com.LukeLabs.PayMeAPI.extensions;

import java.time.LocalDateTime;

public class CardExtensions {
    public static int MonthFromDateTime(LocalDateTime dateTime) {
        return dateTime.getMonth().getValue();
    }

    public static int YearFromDateTime(LocalDateTime dateTime) {
        return dateTime.getYear();
    }
}
