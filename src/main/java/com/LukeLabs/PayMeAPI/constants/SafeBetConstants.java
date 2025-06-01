package com.LukeLabs.PayMeAPI.constants;

import java.util.List;

public class SafeBetConstants {

    public static class Categories {
        public static final String E_GAMING = "egaming";
        public static final List<String> All = List.of(E_GAMING);
    }

    public static final double PER_DAY_TOTAL_SPEND_LIMIT = 1000;
    public static final int PER_DAY_SPEND_COUNT_LIMIT = 5;
}
