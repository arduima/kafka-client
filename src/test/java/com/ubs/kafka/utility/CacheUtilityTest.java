package com.ubs.kafka.utility;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

/**
 * Created by dkoshkin on 4/11/16.
 */
public class CacheUtilityTest {

    @Test
    public void newCache() throws Exception {
        assertNotNull(CacheUtility.newCache());
    }

    @Test
    public void keyExistsIn() throws Exception {
        assertFalse(CacheUtility.keyExists(null, CacheUtility.newCache(), false));
        assertFalse(CacheUtility.keyExists("topic1", null, false));
        assertFalse(CacheUtility.keyExists(null, null, false));

        ConcurrentHashMap<String, Long> cache2 = CacheUtility.newCache();
        assertFalse(CacheUtility.keyExists("topic2", cache2, false));
        assertFalse(CacheUtility.keyExists("topic2", cache2, false));

        ConcurrentHashMap<String, Long> cache3 = CacheUtility.newCache();
        assertFalse(CacheUtility.keyExists("topic3", cache3, true));
        assertTrue(CacheUtility.keyExists("topic3", cache3, true));
    }

    @Test
    public void deleteKeyFromCache() throws Exception {
        assertNull(CacheUtility.deleteKey(null, CacheUtility.newCache()));
        assertNull(CacheUtility.deleteKey("topic1", CacheUtility.newCache()));
        assertNull(CacheUtility.deleteKey(null, null));

        ConcurrentHashMap<String, Long> cache1 = CacheUtility.newCache();
        assertNull(CacheUtility.deleteKey("topic1", null));
        assertNull(CacheUtility.deleteKey(null, CacheUtility.newCache()));
        assertNull(CacheUtility.deleteKey(null, null));

        ConcurrentHashMap<String, Long> cache2 = CacheUtility.newCache();
        assertNull(CacheUtility.deleteKey("topic2", cache2));
        CacheUtility.putIfAbsent("topic2", cache2);
        assertNotNull(CacheUtility.deleteKey("topic2", cache2));

    }
}