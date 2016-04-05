package com.ubs.kafka.utility;

/**
 * Created by dkoshkin on 4/4/16.
 */
public final class Constants {
    private Constants() {}

    /* Zookeeper */
    public static final int ZK_SESSION_TIMEOUT = 1000;
    public static final int ZK_CONNECTION_TIMEOUT = 1000;
    public static final boolean ZK_IS_SECURITY_ENABLED = false;

    /* Topic */
    public static final int TOPIC_PARTITIONS = 1;
    public static final int TOPIC_REPLICATION_FACTOR = 1;

    /* Logger Messages */
    public static final String LOGGER_CREATE_TOPIC_WARN = "Topic does not exist, created topic ";
    public static final String LOGGER_TOPIC_NULL_WARN = "Topic is NULL";
    public static final String LOGGER_ZKUTILS_NULL_WARN = "ZkUtils is NULL";

}
