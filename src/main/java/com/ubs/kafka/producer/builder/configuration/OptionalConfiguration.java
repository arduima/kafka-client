package com.ubs.kafka.producer.builder.configuration;

import com.ubs.kafka.preset.KafkaAcknowledgements;
import org.apache.kafka.clients.producer.Partitioner;

/**
 * Created by dkoshkin on 4/2/16.
 */
public interface OptionalConfiguration<K, V> extends SerializerConfiguration<K, V>, ZookeeperConfiguration<K, V>, Build<K, V>, BuildFromFile<K, V> {

    OptionalConfiguration<K, V> partitioner(Partitioner partitioner);

    OptionalConfiguration<K, V> acknowledgements(String acknowledgements);
    OptionalConfiguration<K, V> acknowledgements(KafkaAcknowledgements acknowledgementsEnum);
    OptionalConfiguration<K, V> bufferMemory(Integer bytes);
    OptionalConfiguration<K, V> compressionType(String type);
    OptionalConfiguration<K, V> retries(Integer retries);
    OptionalConfiguration<K, V> batchSize(Integer messages);
    OptionalConfiguration<K, V> clientId(String id);
    OptionalConfiguration<K, V> connectionsMaxIdle(Integer milliseconds);
    OptionalConfiguration<K, V> linger(Integer milliseconds);
    OptionalConfiguration<K, V> maxBlock(Integer milliseconds);
    OptionalConfiguration<K, V> maxRequestSize(Integer bytes);
    OptionalConfiguration<K, V> receiveBuffer(Integer bytes);
    OptionalConfiguration<K, V> requestTimeout(Integer milliseconds);
    OptionalConfiguration<K, V> sendBuffer(Integer bytes);
    OptionalConfiguration<K, V> timeout(Integer milliseconds);
    OptionalConfiguration<K, V> blockOnBufferFull(Boolean block);
    OptionalConfiguration<K, V> maxInFlightRequestsPerConnection(Integer requests);
    OptionalConfiguration<K, V> metadataFetchTimeout(Integer milliseconds);
    OptionalConfiguration<K, V> metadataMaxAge(Integer milliseconds);
    OptionalConfiguration<K, V> metricReporters(String reporters);
    OptionalConfiguration<K, V> metricsSampleWindow(Integer milliseconds);
    OptionalConfiguration<K, V> metricsNumSamples(Integer samples);
    OptionalConfiguration<K, V> reconnectBackoff(Integer milliseconds);
    OptionalConfiguration<K, V> retryBackoff(Integer milliseconds);

    OptionalConfiguration<K, V> securityProtocol(String protocol);

    SASLConfiguration<K, V> sasl();
    SSLConfiguration<K, V> ssl();

    CustomConfiguration<K, V> custom();

}