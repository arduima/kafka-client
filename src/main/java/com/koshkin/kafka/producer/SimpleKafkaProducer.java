package com.koshkin.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Properties;

/**
 * Created by dkoshkin
 * Simple Kafka producer that allows for an easy configuration
 * Currently it just calls the super's constructor, but can be later used for further customization
 */
class SimpleKafkaProducer<K, V> extends KafkaProducer<K, V> {

    SimpleKafkaProducer(Properties properties, Serializer<K> keySerializer, Serializer<V> valueSerializer) {
        super(properties, keySerializer, valueSerializer);
    }

}
