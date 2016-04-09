package com.ubs.kafka.producer.builder;

import com.ubs.kafka.KafkaClinetTest;
import com.ubs.kafka.producer.SimpleProducer;
import com.ubs.kafka.utility.TopicUtility;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by dkoshkin on 4/8/16.
 */
public class SimpleKafkaProducerTest extends KafkaClinetTest {

    @Test
    public void send() {
        SimpleProducer<String, String> producer = newProducer();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, 0, "key0_1", "value0_1");
        producer.send(producerRecord);
        producerRecord = new ProducerRecord<>(TOPIC, "key0_2", "value0_2");
        producer.send(producerRecord);
        producerRecord = new ProducerRecord<>(TOPIC, "value0_3");
        producer.send(producerRecord);
        assertTrue(true);
    }

    @Test
    public void send1() throws Exception {
        SimpleProducer<String, String> producer = newProducer();
        Callback callback = newCallback();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, 0, "key1_1", "value1_1");
        Future<RecordMetadata> future = producer.send(producerRecord, callback);
        while(!future.isDone()){
        }
        producerRecord = new ProducerRecord<>(TOPIC, "key1_2", "value1_2");
        future = producer.send(producerRecord, callback);
        while(!future.isDone()){
        }
        producerRecord = new ProducerRecord<>(TOPIC, "value1_3");
        future = producer.send(producerRecord, callback);
        while(!future.isDone()){
        }
        producer.close();
    }

    @Test
    public void send2() throws Exception {
        SimpleProducer<String, String> producer = newProducer();
        producer.send(TOPIC, 0, "key2_1", "value2_1");
        assertTrue(true);
        producer.close();
    }

    @Test
    public void send3() throws Exception {
        SimpleProducer<String, String> producer = newProducer();
        producer.send(TOPIC, "key3_1", "value3_1");
        assertTrue(true);
        producer.close();
    }

    @Test
    public void send4() throws Exception {
        SimpleProducer<String, String> producer = newProducer();
        producer.send(TOPIC, "value4_1");
        assertTrue(true);
        producer.close();
    }

    @Test
    public void send5() throws Exception {
        SimpleProducer<String, String> producer = newProducer();
        Callback callback = newCallback();
        Future<RecordMetadata> future = producer.send(TOPIC, 0, "key5_1", "value5_1", callback);
        while(!future.isDone()){}
        assertTrue(true);
        producer.close();
    }

    @Test
    public void send6() throws Exception {
        SimpleProducer<String, String> producer = newProducer();
        Callback callback = newCallback();
        Future<RecordMetadata> future = producer.send(TOPIC, "key6_1", "value6_1", callback);
        while(!future.isDone()){}
        assertTrue(true);
        producer.close();
    }

    @Test
    public void send7() throws Exception {
        SimpleProducer<String, String> producer = newProducer();
        Callback callback = newCallback();
        Future<RecordMetadata> future = producer.send(TOPIC, "value7_1", callback);
        while(!future.isDone()){}
        assertTrue(true);
        producer.close();
    }

    @Test
    public void createNewAndSend1() throws Exception {
        TopicUtility.createTopic(NEW_TOPIC1, ZK_SERVERS);
        SimpleProducer<String, String> producer = newProducer();
        Future<RecordMetadata> future = producer.send(NEW_TOPIC1, "createNewAndSend_value1");
        while(!future.isDone()){}
        assertTrue(true);
        producer.close();
    }

    @Test
    public void createNewAndSend2() throws Exception {
        SimpleProducer<String, String> producer = newProducerZK();
        producer.createTopic(NEW_TOPIC2);
        Future<RecordMetadata> future = producer.send(NEW_TOPIC2, "createNewAndSend_value2");
        while(!future.isDone()){}
        assertTrue(true);
        producer.close();
    }

    @Test(expected = IllegalStateException.class)
    public void close() throws Exception {
        SimpleProducer<String, String> producer = newProducer();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, "value_close0");
        producer.send(producerRecord);
        assertTrue(true);

        producer.close();
        // This should thrown an exception
        producer.send(producerRecord);
    }

    @Test(expected = IllegalStateException.class)
    public void close1() throws Exception {
        SimpleProducer<String, String> producer = newProducer();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, "value_close1");
        producer.send(producerRecord);
        assertTrue(true);

        producer.close(1000, TimeUnit.MILLISECONDS);
        // This should thrown an exception
        producer.send(producerRecord);

    }

    @Test(expected = IllegalStateException.class)
    public void closeable() throws Exception {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, "value_closeable");

        SimpleProducer<String, String> spyProducer;
        try(SimpleProducer<String, String> producer = newProducer()) {
            spyProducer = producer;
            producer.send(producerRecord);
            assertTrue(true);
        }
        // This should thrown an exception
        spyProducer.send(producerRecord);
    }


    private Callback newCallback() {
        Callback callback = new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                assertNotNull(metadata);
                assertEquals(TOPIC, metadata.topic());
            }
        };
        return callback;
    }

    private SimpleProducer<String, String> newProducer() {
       return new KafkaProducerBuilder<String, String>().newProducer()
                .servers(SERVERS)
                .build();
    }

    private SimpleProducer<String, String> newProducerZK() {
        return new KafkaProducerBuilder<String, String>().newProducer()
                .servers(SERVERS)
                .zookeeperServers(ZK_SERVERS)
                .build();
    }
}