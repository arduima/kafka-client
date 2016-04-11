package com.ubs.kafka.utility;

/**
 * Created by dkoshkin on 4/4/16.
 */
public final class Constants {
    private Constants() {}

    /* Zookeeper */
    public static final int ZK_SESSION_TIMEOUT = 6000;
    public static final int ZK_CONNECTION_TIMEOUT = 3000;
    public static final boolean ZK_IS_SECURITY_ENABLED = false;

    /* Topic */
    public static final int TOPIC_PARTITIONS = 8;
    public static final int TOPIC_REPLICATION_FACTOR = 1;
    // ms for topic to be ready after creating
    public static final int CREATE_TOPIC_TIMEOUT = 3000;

    /* Logger Messages */
    public static final String LOGGER_CREATE_TOPIC_WARN = "Topic does not exist, created topic ";
    public static final String LOGGER_TOPIC_NULL_ERROR = "Topic cannot be NULL";
    public static final String LOGGER_ZKUTILS_NULL_ERROR = "ZkUtils cannot NULL";
    public static final String LOGGER_TOPIC_CREATE_WARN = "Topic was not created ";

}
