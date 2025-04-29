package com.LukeLabs.PayMeAPI.constants;

import java.util.List;

public class CardStatusConstants {
    public final static String ACTIVE = "ACTIVE";
    public final static String INACTIVE = "INACTIVE";
    public final static String CANCELLED = "CANCELLED";

    public final static List<String> all = List.of(ACTIVE, INACTIVE, CANCELLED);
}
