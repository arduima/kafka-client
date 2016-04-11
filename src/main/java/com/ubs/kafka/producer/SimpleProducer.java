package com.ubs.kafka.producer;

import org.apache.kafka.clients.producer.Producer;

import java.io.Closeable;

/**
 * Created by de08300 on 4/1/2016.
 */
public interface SimpleProducer<K, V> extends Sender<K, V>, Producer<K, V>, Closeable {

}
