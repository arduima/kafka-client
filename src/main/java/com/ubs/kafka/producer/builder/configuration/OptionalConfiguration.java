package com.ubs.kafka.producer.builder.configuration;

import com.ubs.kafka.preset.KafkaAcknowledgements;
import com.ubs.kafka.preset.KafkaSerializers;
import org.apache.kafka.common.serialization.Serializer;

/**
 * Created by dkoshkin on 4/2/16.
 */
public interface OptionalConfiguration<K, V> extends Build<K, V>, SerializerConfiguration<K, V> {
    OptionalConfiguration<K, V> valueSerializer(Serializer<V> valueSerializer);
    OptionalConfiguration<K, V> valueSerializer(KafkaSerializers serializerEnum);
    OptionalConfiguration<K, V> acknowledgements(String acknowledgements);
    OptionalConfiguration<K, V> acknowledgements(KafkaAcknowledgements acknowledgementsEnum);
    OptionalConfiguration<K, V> retries(Integer retries);
    OptionalConfiguration<K, V> batchSize(Integer batchedMessages);
    OptionalConfiguration<K, V> linger(Integer milliseconds);
    OptionalConfiguration<K, V> buffer(Integer bytes);

    CustomConfiguration<K, V> custom();
}