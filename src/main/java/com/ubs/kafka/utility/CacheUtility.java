package com.ubs.kafka.utility;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dkoshkin on 4/11/16.
 */
public class CacheUtility {

    private CacheUtility() {}

    public static ConcurrentHashMap<String, Long> newCache() {
        return new ConcurrentHashMap<>();
    }

    // If putIfAbsent() doesn't return null, the key is in Map
    // TODO fix error when using Map
    public static Boolean keyExists(String key, ConcurrentHashMap<String, Long> cache, boolean put) {
        if(cache == null || key == null) {
            return false;
        }
        // Auto create key, value
        if(put) {
            return putIfAbsent(key, cache) != null;
        }
        else {
            return cache.get(key) != null;
        }
    }

    public static Long putIfAbsent(String key, ConcurrentHashMap<String, Long> cache) {
        return cache.putIfAbsent(key, System.currentTimeMillis());
    }

    public static Long deleteKey(String key, ConcurrentHashMap<String, Long> cache) {
        if(cache == null || key == null) {
            return null;
        }
         Long value = cache.get(key);
         cache.remove(key);

         return value;
    }
}
