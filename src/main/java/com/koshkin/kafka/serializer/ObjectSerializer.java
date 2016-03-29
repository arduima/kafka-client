package com.koshkin.kafka.serializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.*;
import java.util.Map;

/**
 * Created by dkoshkin
 */
public class ObjectSerializer<T> implements Serializer<T>, Deserializer<T> {

    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to do
    }

    public byte[] serialize(String topic, T data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(out);
        } catch (IOException e) {
            throw new SerializationException("Serialization exception");
        }
        try {
            os.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public T deserialize(String topic, byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(in);
        } catch (IOException e) {
            throw new SerializationException("Serialization exception");
        }
        try {
            return (T) is.readObject();
        } catch (IOException e) {
            throw new SerializationException("Serialization exception");
        } catch (ClassNotFoundException e) {
            throw new SerializationException("Serialization exception, class not found");
        }
    }

    public void close() {
        // nothing to do
    }
}
