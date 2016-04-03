package com.ubs.kafka.producer.builder.configuration;

/**
 * Created by dkoshkin on 4/2/16.
 */
public interface SSLConfiguration<K, V> extends NestedConfiguration<K, V> {

    SSLConfiguration<K, V> keyPassword(String password);
    SSLConfiguration<K, V> keystoreLocation(String location);
    SSLConfiguration<K, V> keystorePassword(String password);
    SSLConfiguration<K, V> truststoreLocation(String location);
    SSLConfiguration<K, V> truststorePassword(String password);
    SSLConfiguration<K, V> enabledProtocols(String protocolList);
    SSLConfiguration<K, V> keystoreType(String type);
    SSLConfiguration<K, V> protocol(String protocol);
    SSLConfiguration<K, V> provider(String provider);
    SSLConfiguration<K, V> truststoreType(String type);
    SSLConfiguration<K, V> cipherSuites(String suiteList);
    SSLConfiguration<K, V> endpointIdentificationAlgorithm(String algorithm);
    SSLConfiguration<K, V> keymanagerAlgorithm(String algorithm);
    SSLConfiguration<K, V> trustmanagerAlgorithm(String algorithm);

}
