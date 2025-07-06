package com.LukeLabs.PayMeAPI.utilities.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LimitedCache<K, V> extends LinkedHashMap<K,V> {
    private final int maxCacheSize;

    public LimitedCache(int maxCacheSize) {
        super(maxCacheSize + 1, 0.75f, false);
        this.maxCacheSize = maxCacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return size() > maxCacheSize;
    }
}
