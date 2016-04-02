package com.ubs.kafka.producer.builder;

import com.ubs.kafka.producer.SimpleProducer;
import com.ubs.kafka.producer.builder.configuration.ServerConfiguration;

/**
 * Created by dkoshkin
 * KafkaProducerBuilder using the step building pattern
 * Must be instantiated to be handle generic parameters
 * Required Parameters: _Configuration with a method returning a different _Configuration
 * Optional Parameters: a method in OptionalConfiguration that returns OptionalConfiguration
 * TODO add documentation
 */
public class KafkaProducerBuilder<K, V> {

    public KafkaProducerBuilder(){}

    public ServerConfiguration<K, V> newProducer() {
        return new ProducerConfiguration<K, V>();
    }

    public SimpleProducer<K, V> newProducerFromFile(String path) {
        return new ProducerConfiguration<K, V>().build(path);
    }

}
