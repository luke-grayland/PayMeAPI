package com.LukeLabs.PayMeAPI.enums;

import java.util.HashMap;
import java.util.Map;

public class TransactionTypes {
    public static final Map<Integer, String> Values = new HashMap<Integer, String>();

    static {
        Values.put(0, "Spend");
        Values.put(1, "Refund");
        Values.put(2, "Reversal");
        Values.put(3, "Chargeback");
        Values.put(4, "Account Transfer");
        Values.put(5, "Unknown");
    }

    private TransactionTypes() {}
}
