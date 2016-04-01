package com.ubs.kafka.serializer;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dkoshkin on 3/28/16.
 */
public class ObjectSerializerTest {

    private static final String SOME_STRING = "TEST string";

    @Test
    public void serialize() throws Exception {
        Serializer<String> serializer = new ObjectSerializer<>();
        byte[] bytes = serializer.serialize(null, SOME_STRING);
        assertNotNull(bytes);

        Deserializer<String> deserializer = new ObjectSerializer<>();
        String originalMessage = deserializer.deserialize(null, bytes);
        assertEquals(SOME_STRING, originalMessage);
    }

}