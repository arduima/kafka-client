package com.ubs.kafka.producer.builder.configuration;

/**
 * Created by dkoshkin on 4/3/16.
 */
public interface SASLConfiguration<K, V> extends NestedConfiguration<K, V> {

    SASLConfiguration<K, V> kerberosServiceName(String name);
    SASLConfiguration<K, V> kerberosKinitCmd(String command);
    SASLConfiguration<K, V> kerberosMinTimeBeforeRelogin(Integer milliseconds);
    SASLConfiguration<K, V> kerberosTicketRenewJitter(Double percentage);
    SASLConfiguration<K, V> kerberosTicketRenewWindowFactor(Double factor);

}
