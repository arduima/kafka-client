package com.ubs.kafka.producer.builder;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by dkoshkin
 * Simple Kafka producer that allows for an easy configuration
 * Currently it just calls the super's constructor, but can be later used for further customization
 */
class SimpleKafkaProducer<K, V> extends KafkaProducer<K, V> implements SimpleProducer<K, V> {

    private final ZkUtils zkUtils;
    private final ConcurrentHashMap<String, Long> topicCache;

    SimpleKafkaProducer(Properties properties, Serializer<K> keySerializer, Serializer<V> valueSerializer, ZkUtils zkUtils) {
        super(properties, keySerializer, valueSerializer);
        this.zkUtils = zkUtils;
        this.topicCache = newTopicMap();
    }

    SimpleKafkaProducer(Properties properties, ZkUtils zkUtils) {
        super(properties);
        this.zkUtils = zkUtils;
        this.topicCache = newTopicMap();
    }

    @Override
    public Future<RecordMetadata> send(ProducerRecord<K, V> record) {
        createTopicIfNotExist(getTopic(record));
        return super.send(record);
    }

    @Override
    public Future<RecordMetadata> send(ProducerRecord<K, V> record, Callback callback) {
        createTopicIfNotExist(getTopic(record));
        return super.send(record, callback);
    }

    @Override
    public Future<RecordMetadata> send(String topic, Integer partition, K key, V value) {
        createTopicIfNotExist(topic);
        return super.send(new ProducerRecord<>(topic, partition, key, value));
    }

    @Override
    public Future<RecordMetadata> send(String topic, K key, V value) {
        createTopicIfNotExist(topic);
        return super.send(new ProducerRecord<>(topic, key, value));
    }

    @Override
    public Future<RecordMetadata> send(String topic, V value) {
        createTopicIfNotExist(topic);
        return super.send(new ProducerRecord<K, V>(topic, value));
    }

    @Override
    public Future<RecordMetadata> send(String topic, Integer partition, K key, V value, Callback callback) {
        createTopicIfNotExist(topic);
        return super.send(new ProducerRecord<>(topic, partition, key, value), callback);
    }

    @Override
    public Future<RecordMetadata> send(String topic, K key, V value, Callback callback) {
        createTopicIfNotExist(topic);
        return super.send(new ProducerRecord<>(topic, key, value), callback);
    }

    @Override
    public Future<RecordMetadata> send(String topic, V value, Callback callback) {
        createTopicIfNotExist(topic);
        return super.send(new ProducerRecord<K, V>(topic, value), callback);
    }

    public Boolean createTopic(String topic) {
        return createTopicIfNotExist(topic);
    }

    public void deleteTopic(String topic) {
        TopicUtility.deleteTopic(topic, zkUtils, topicCache);
    }

    @Override
    public void close() {
        super.close();
        cleanup();
    }

    @Override
    public void close(long timeout, TimeUnit timeUnit) {
        super.close(timeout, timeUnit);
        ZookeeperUtility.close(this.zkUtils);
        cleanup();
    }

    private ConcurrentHashMap<String, Long> newTopicMap() {
        return new ConcurrentHashMap<>();
    }

    private Boolean createTopicIfNotExist(String topic) {
        return TopicUtility.createTopic(topic, zkUtils, topicCache);
    }

    private String getTopic(ProducerRecord producerRecord) {
        return producerRecord.topic();
    }

    private void cleanup() {
        ZookeeperUtility.close(this.zkUtils);
    }

}
