package com.ubs.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.Future;

/**
 * Created by de08300 on 4/1/2016.
 */
public interface Sender<K, V> {

    Future<RecordMetadata> send(String topic, Integer partition, K key, V value);
    Future<RecordMetadata> send(String topic, K key, V value);
    Future<RecordMetadata> send(String topic, V value);
    Future<RecordMetadata> send(String topic, Integer partition, K key, V value, Callback callback);
    Future<RecordMetadata> send(String topic, K key, V value, Callback callback);
    Future<RecordMetadata> send(String topic, V value,  Callback callback);

}
