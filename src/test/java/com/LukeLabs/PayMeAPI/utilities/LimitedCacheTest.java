package com.LukeLabs.PayMeAPI.utilities;

import com.LukeLabs.PayMeAPI.utilities.cache.LimitedCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LimitedCacheTest {
    @Test
    public void LimitedCache_EntriesExceedMaxCacheSize_CacheSizeEnforced() {
        //Assemble
        int maxCacheSize = 3;
        LimitedCache<Integer, Integer> cache = new LimitedCache<>(maxCacheSize);

        //Act
        cache.put(1, 1001);
        cache.put(2, 1002);
        cache.put(3, 1003);
        cache.put(4, 1004);
        cache.put(5, 1005);

        //Assert
        assertEquals(maxCacheSize, cache.size());
        assertNull(cache.get(1));
        assertNull(cache.get(2));
        assertEquals(1003, cache.get(3));
        assertEquals(1004, cache.get(4));
        assertEquals(1005, cache.get(5));
    }

    @Test
    public void LimitedCache_EntriesCountLessThanMaxCacheSize_AllEntriesRemain() {
        //Assemble
        int maxCacheSize = 10;
        LimitedCache<Integer, Integer> cache = new LimitedCache<>(maxCacheSize);

        //Act
        cache.put(1, 1001);
        cache.put(2, 1002);
        cache.put(3, 1003);
        cache.put(4, 1004);
        cache.put(5, 1005);

        //Assert
        assertEquals(5, cache.size());
        assertEquals(1001, cache.get(1));
        assertEquals(1002, cache.get(2));
        assertEquals(1003, cache.get(3));
        assertEquals(1004, cache.get(4));
        assertEquals(1005, cache.get(5));
    }
}
