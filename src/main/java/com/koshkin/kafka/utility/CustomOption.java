package com.koshkin.kafka.utility;

/**
 * Created by dkoshkin on 3/28/16.
 */
public class CustomOption {
    /**
     * Created by dkoshkin
     */
    private final String key;
    private final Object value;

    public CustomOption(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
