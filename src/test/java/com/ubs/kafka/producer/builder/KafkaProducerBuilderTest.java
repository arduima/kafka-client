package com.ubs.kafka.producer.builder;

import com.ubs.kafka.KafkaClinetTest;
import com.ubs.kafka.exception.PropertiesException;
import com.ubs.kafka.configenum.KafkaAcknowledgements;
import com.ubs.kafka.configenum.KafkaSerializers;
import com.ubs.kafka.producer.SimpleProducer;
import com.ubs.kafka.producer.builder.KafkaProducerBuilder;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.util.concurrent.Future;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by dkoshkin on 3/28/16.
 */
public class KafkaProducerBuilderTest extends KafkaClinetTest {

    //private final static String SERVERS = "172.19.36.21:9092,172.19.36.22:9092,172.19.36.23:9092,172.19.36.24:9092";

    @Test
    public void newBuilder() throws Exception {
        SimpleProducer<String, String> producer1 = new KafkaProducerBuilder<String, String>().newProducer()
                .servers(SERVERS)
                .build();
        assertNotNull(producer1);
        Future<RecordMetadata> future1 = producer1.send(TOPIC, "newBuilder1");
        while(!future1.isDone()){}
        producer1.close();

        SimpleProducer<String, byte[]> producer2 = new KafkaProducerBuilder<String, byte[]>().newProducer()
                .servers(SERVERS)
                .keySerializer(new StringSerializer())
                .valueSerializer(new ByteArraySerializer())
                .build();
        assertNotNull(producer2);
        Future<RecordMetadata> future2 = producer2.send(TOPIC, new byte[0]);
        while(!future2.isDone()){}
        producer2.close();

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
        Future<RecordMetadata> future3 = producer3.send(TOPIC, Integer.MAX_VALUE);
        while(!future3.isDone()){}
        producer3.close();
    }

    @Test
    public void newBuilderFromFile() {
        SimpleProducer<String, String> producer = new KafkaProducerBuilder<String, String>().newProducerFromFile(PROPERTIES_FILE);
        assertNotNull(producer);
        Future<RecordMetadata> future = producer.send(TOPIC, "newBuilderFromFile");
        while(!future.isDone()){}
        producer.close();
    }

    @Test(expected = NullPointerException.class)
    public void newBuilderFromFileNullPointerException() {
        SimpleProducer<String, String> producer = new KafkaProducerBuilder<String, String>().newProducerFromFile(null);
    }

    @Test(expected = PropertiesException.class)
    public void newBuilderFromFilePropertiesException() {
        SimpleProducer<String, String> producer = new KafkaProducerBuilder<String, String>().newProducerFromFile("/incorrect/path/to/kafka.properties");
    }

}