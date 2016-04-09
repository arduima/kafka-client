package com.ubs.kafka.producer.builder;

import com.ubs.kafka.producer.SimpleProducer;
import com.ubs.kafka.utility.TopicUtility;
import com.ubs.kafka.utility.ZookeeperUtility;
import kafka.utils.ZkUtils;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Properties;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by dkoshkin
 * Simple Kafka producer that allows for an easy configuration
 * Currently it just calls the super's constructor, but can be later used for further customization
 */
class SimpleKafkaProducer<K, V> extends KafkaProducer<K, V> implements SimpleProducer<K, V> {

    private final ZkUtils zkUtils;

    SimpleKafkaProducer(Properties properties, Serializer<K> keySerializer, Serializer<V> valueSerializer, ZkUtils zkUtils) {
        super(properties, keySerializer, valueSerializer);
        this.zkUtils = zkUtils;
    }

    SimpleKafkaProducer(Properties properties, ZkUtils zkUtils) {
        super(properties);
        this.zkUtils = zkUtils;
    }

    @Override
    public Future<RecordMetadata> send(ProducerRecord<K, V> record) {
        return super.send(record);
    }

    @Override
    public Future<RecordMetadata> send(ProducerRecord<K, V> record, Callback callback) {
        return super.send(record, callback);
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

    @Override
    public Boolean createTopic(String topic) {
        return TopicUtility.createTopicIfNotExist(topic, zkUtils);
    }

    @Override
    public void close() {
        super.close();
        ZookeeperUtility.close(this.zkUtils);
    }

    @Override
    public void close(long timeout, TimeUnit timeUnit) {
        super.close(timeout, timeUnit);
        ZookeeperUtility.close(this.zkUtils);
    }

}
