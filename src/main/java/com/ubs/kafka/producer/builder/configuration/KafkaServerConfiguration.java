package com.ubs.kafka.producer.builder.configuration;

import com.ubs.kafka.configenum.KafkaServers;

/**
 * Created by dkoshkin on 4/2/16.
 */
public interface KafkaServerConfiguration<K, V> {
    OptionalConfiguration<K, V> servers(String serverList);
    OptionalConfiguration<K, V> servers(KafkaServers serversEnum);
}