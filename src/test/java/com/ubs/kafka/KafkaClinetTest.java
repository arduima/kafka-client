package com.ubs.kafka;

/**
 * Created by dkoshkin on 4/8/16.
 */
public class KafkaClinetTest {

    protected static final String SERVERS = "192.168.99.100:32806";
    public static final String ZK_SERVERS = "192.168.99.100:32805";
    protected static final String TOPIC = "test_topic";
    protected static final String NEW_TOPIC1 = "new_test_topic1";
    protected static final String NEW_TOPIC2 = "new_test_topic2";
    public static final String TOPIC_UTILITY_TOPIC = "topic_utility_topic";
    public static final String TOPIC_UTILITY_TOPIC_URL = "topic_utility_topic_url";

    public static final String PROPERTIES_FILE = "src/test/resources/kafka-test.properties";

    public static final String SOME_STRING = "TEST string";

    // LOG MESSAGES
    public static final String CALLBACK_GOOD = "CALLBACK Reached ";
}
