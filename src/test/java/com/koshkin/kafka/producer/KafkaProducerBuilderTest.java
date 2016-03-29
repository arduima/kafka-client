package com.koshkin.kafka.producer;

import com.koshkin.kafka.serializer.KafkaSerializers;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dkoshkin on 3/28/16.
 */
public class KafkaProducerBuilderTest {

    public static final String SERVERS = "192.168.99.100:32769";

    @Test
    public void newBuilder() throws Exception {
        Producer<String, String> producer1 = new KafkaProducerBuilder<String, String>().newBuilder()
                .servers(SERVERS)
                .build();
        assertNotNull(producer1);

        Producer<String, byte[]> producer2 = new KafkaProducerBuilder<String, byte[]>().newBuilder()
                .servers(SERVERS)
                .keySerializer(new StringSerializer())
                .valueSerializer(new ByteArraySerializer())
                .build();
        assertNotNull(producer2);

        // Key & Value Serializers can be any Serializable object or byte[]
        Producer<String, Integer> producer3 = new KafkaProducerBuilder<String, Integer>().newBuilder()
                .servers(SERVERS)
                .acknowledgements("all")
                .batchSize(16384)
                .buffer(1024)
                .linger(1)
                .retries(0)
                .keySerializer(KafkaSerializers.STRING)
                .valueSerializer(KafkaSerializers.OBJECT)
                .custom()
                    .option("optionKey1", "optionValue1")
                    .option("optionKey2", "optionValue2")
                    .and()
                .build();
        assertNotNull(producer3);
    }
}