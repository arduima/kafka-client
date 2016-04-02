package com.ubs.kafka.producer.builder.configuration;

import com.ubs.kafka.producer.SimpleProducer;

/**
 * Created by dkoshkin on 4/2/16.
 */
public interface Build<K, V> {
    SimpleProducer<K, V> build();
}
