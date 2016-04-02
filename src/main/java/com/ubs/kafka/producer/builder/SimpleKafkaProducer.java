package com.ubs.kafka.producer.builder;

import com.ubs.kafka.producer.SimpleProducer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Created by dkoshkin
 * Simple Kafka producer that allows for an easy configuration
 * Currently it just calls the super's constructor, but can be later used for further customization
 */
class SimpleKafkaProducer<K, V> extends KafkaProducer<K, V> implements SimpleProducer<K, V> {

    SimpleKafkaProducer(Properties properties, Serializer<K> keySerializer, Serializer<V> valueSerializer) {
        super(properties, keySerializer, valueSerializer);
    }

    SimpleKafkaProducer(Properties properties) {
        super(properties);
    }

    @Override
    public Future<RecordMetadata> send(String topic, Integer partition, K key, V value) {
        return super.send(new ProducerRecord<>(topic, partition, key, value));
    }

    @Override
    public Future<RecordMetadata> send(String topic, K key, V value) {
        return super.send(new ProducerRecord<>(topic, key, value));
    }

    @Override
    public Future<RecordMetadata> send(String topic, V value) {
        return super.send(new ProducerRecord<K, V>(topic, value));
    }

    @Override
    public Future<RecordMetadata> send(String topic, Integer partition, K key, V value, Callback callback) {
        return super.send(new ProducerRecord<>(topic, partition, key, value), callback);
    }

    @Override
    public Future<RecordMetadata> send(String topic, K key, V value, Callback callback) {
        return super.send(new ProducerRecord<>(topic, key, value), callback);
    }

    @Override
    public Future<RecordMetadata> send(String topic, V value, Callback callback) {
        return super.send(new ProducerRecord<K, V>(topic, value), callback);
    }
}
