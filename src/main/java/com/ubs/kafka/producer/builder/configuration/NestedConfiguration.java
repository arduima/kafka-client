package com.ubs.kafka.producer.builder.configuration;

/**
 * Created by dkoshkin on 4/2/16.
 */
public interface NestedConfiguration<K, V> {

    /*Keyword to go back*/
    OptionalConfiguration<K, V> and();

}
