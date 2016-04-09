package com.ubs.kafka.utility;

import com.ubs.kafka.KafkaClinetTest;
import kafka.utils.ZkUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dkoshkin on 4/9/16.
 */
public class TopicUtilityTest extends KafkaClinetTest {
    ZkUtils zkUtils;
    @Before
    public void setUp() throws Exception {
        zkUtils = ZookeeperUtility.newZkUtils(ZK_SERVERS);

    }

    @Test
    public void createTopicIfNotExist() throws Exception {
        assertTrue(TopicUtility.createTopicIfNotExist(TOPIC_UTILITY_TOPIC, zkUtils));
        assertFalse(TopicUtility.createTopicIfNotExist(TOPIC_UTILITY_TOPIC, zkUtils));
    }

    @Test
    public void createTopicIfNotExistURL() throws Exception {
        assertTrue(TopicUtility.createTopicIfNotExist(TOPIC_UTILITY_TOPIC_URL, ZK_SERVERS));
        assertFalse(TopicUtility.createTopicIfNotExist(TOPIC_UTILITY_TOPIC_URL, ZK_SERVERS));
    }

    @Test(expected = IllegalStateException.class)
    public void topicNull() throws Exception {
        TopicUtility.createTopicIfNotExist(null, zkUtils);
    }

    @Test(expected = IllegalStateException.class)
    public void topicNullUrl() throws Exception {
        TopicUtility.createTopicIfNotExist(null, ZK_SERVERS);
    }

}