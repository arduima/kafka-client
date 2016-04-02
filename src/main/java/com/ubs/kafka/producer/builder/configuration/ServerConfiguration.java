package com.ubs.kafka.producer.builder.configuration;

import com.ubs.kafka.preset.KafkaServers;

/**
 * Created by dkoshkin on 4/2/16.
 */
public interface ServerConfiguration<K, V> {
    OptionalConfiguration<K, V> servers(String servers);
    OptionalConfiguration<K, V> servers(KafkaServers serversEnum);
}