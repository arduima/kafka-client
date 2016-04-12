package com.ubs.kafka.utility;

import com.ubs.kafka.KafkaClientTest;
import kafka.utils.ZkUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

/**
 * Created by dkoshkin on 4/9/16.
 */
public class TopicUtilityTest extends KafkaClientTest {
    private ZkUtils zkUtils;
    private ConcurrentHashMap<String, Long> topicCache;

    @Before
    public void setUp() throws Exception {
        zkUtils = ZookeeperUtility.newZkUtils(ZK_SERVERS);
        topicCache = new ConcurrentHashMap<>();

    }

    @Test
    public void createTopicIfNotExist() throws Exception {
        assertTrue(TopicUtility.createTopic(TOPIC_UTILITY_TOPIC, zkUtils, topicCache));
        assertFalse(TopicUtility.createTopic(TOPIC_UTILITY_TOPIC, zkUtils, topicCache));
    }

    @Test
    public void createTopicIfNotExistURL() throws Exception {
        assertTrue(TopicUtility.createTopic(TOPIC_UTILITY_TOPIC_URL, ZK_SERVERS, topicCache));
        assertFalse(TopicUtility.createTopic(TOPIC_UTILITY_TOPIC_URL, ZK_SERVERS, topicCache));
    }

    @Test
    public void deleteTopic() throws Exception {
        assertTrue(TopicUtility.createTopic(TOPIC_UTILITY_DELETE, ZK_SERVERS, topicCache));
        assertTrue(TopicUtility.deleteTopic(TOPIC_UTILITY_DELETE, ZK_SERVERS, topicCache));
    }

    @Test(expected = IllegalStateException.class)
    public void topicNull() throws Exception {
        TopicUtility.createTopic(null, zkUtils, topicCache);
    }

    @Test(expected = IllegalStateException.class)
    public void topicNullUrl() throws Exception {
        TopicUtility.createTopic(null, ZK_SERVERS, topicCache);
    }

}