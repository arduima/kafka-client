package com.ubs.kafka.producer;

import com.ubs.kafka.exception.PropertiesException;
import com.ubs.kafka.preset.KafkaAcknowledgements;
import com.ubs.kafka.preset.KafkaSerializers;
import com.ubs.kafka.producer.builder.KafkaProducerBuilder;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by dkoshkin on 3/28/16.
 */
public class KafkaProducerBuilderTest {

    //public static final String SERVERS = "192.168.99.100:32769";
    private final static String SERVERS = "172.19.36.21:9092,172.19.36.22:9092,172.19.36.23:9092,172.19.36.24:9092";

    @Test
    public void newBuilder() throws Exception {
        SimpleProducer<String, String> producer1 = new KafkaProducerBuilder<String, String>().newProducer()
                .servers(SERVERS)
                .build();
        assertNotNull(producer1);

        SimpleProducer<String, byte[]> producer2 = new KafkaProducerBuilder<String, byte[]>().newProducer()
                .servers(SERVERS)
                .keySerializer(new StringSerializer())
                .valueSerializer(new ByteArraySerializer())
                .build();
        assertNotNull(producer2);

        // Key & Value Serializers can be any Serializable object or byte[]
        SimpleProducer<String, Integer> producer3 = new KafkaProducerBuilder<String, Integer>().newProducer()
                .servers(SERVERS)
                .acknowledgements("all")
                .batchSize(16384)
                .bufferMemory(1024)
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

        SimpleProducer<String, byte[]> producer4 = new KafkaProducerBuilder<String, byte[]>().newProducer()
                .servers(SERVERS)
                .acknowledgements(KafkaAcknowledgements.ALL)
                .build();
        assertNotNull(producer4);

    }

    @Test(expected = NullPointerException.class)
    public void newBuilderFromFileNullPointerException() {
        SimpleProducer<String, String> producer = new KafkaProducerBuilder<String, String>().newProducerFromFile(null);
    }

    @Test(expected = PropertiesException.class)
    public void newBuilderFromFilePropertiesException() {
        SimpleProducer<String, String> producer = new KafkaProducerBuilder<String, String>().newProducerFromFile("/incorrect/path/to/kafka.properties");
    }

    // TODO test happy-path for newBuilderFromFile
}