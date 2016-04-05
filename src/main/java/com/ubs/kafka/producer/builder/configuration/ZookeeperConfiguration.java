package com.ubs.kafka.producer.builder.configuration;

import com.ubs.kafka.configenum.ZookeeperServers;

/**
 * Created by dkoshkin on 4/4/16.
 */
public interface ZookeeperConfiguration<K, V> {
    OptionalConfiguration<K, V> zookeeperServers(String serverList);
    OptionalConfiguration<K, V> zookeeperServers(ZookeeperServers serversEnum);
}
