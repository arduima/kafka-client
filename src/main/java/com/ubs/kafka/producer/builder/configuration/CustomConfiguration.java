package com.ubs.kafka.producer.builder.configuration;

/**
 * Created by dkoshkin on 4/2/16.
 */
public interface CustomConfiguration<K, V> {
    CustomConfiguration<K, V> option(String key, Object value);

    /*Keyword to go back*/
    OptionalConfiguration<K, V> and();
}
