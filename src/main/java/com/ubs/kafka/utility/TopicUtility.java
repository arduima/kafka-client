package com.ubs.kafka.utility;

import com.ubs.kafka.task.CreateTopicTask;
import kafka.admin.AdminUtils;
import kafka.utils.ZkUtils;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Created by dkoshkin on 4/4/16.
 */
public class TopicUtility {

    public static final Logger LOGGER = Logger.getLogger(TopicUtility.class.getName());

    private TopicUtility() {}

    public static Boolean createTopic(String topic, ZkUtils zkUtils, ConcurrentHashMap<String, Long> cache) {
        // Add the key when looking up
        Boolean topicExists = doesTopicExist(topic, zkUtils, cache, true);
        Boolean topicCreated = Boolean.FALSE;
        if(!topicExists) {
            LOGGER.warning(Constants.LOGGER_CREATE_TOPIC_WARN + topic);
            AdminUtils.createTopic(zkUtils, topic, Constants.TOPIC_PARTITIONS, Constants.TOPIC_REPLICATION_FACTOR, new Properties());

            topicCreated = createTopicTask(topic, zkUtils);
        }

        return topicCreated;
    }

    public static Boolean createTopic(String topic, String zookeeperUrl, ConcurrentHashMap<String, Long> cache) {
        ZkUtils zkUtils = ZookeeperUtility.newZkUtils(zookeeperUrl);

        return createTopic(topic, zkUtils, cache);
    }


    public static Boolean deleteTopic(String topic, ZkUtils zkUtils, ConcurrentHashMap<String, Long> cache) {
        // Don't add the key when looking up, trying to delete it
        Boolean topicExists = doesTopicExist(topic, zkUtils, cache, false);
        if(topicExists) {
            AdminUtils.deleteTopic(zkUtils, topic);
            CacheUtility.deleteKey(topic, cache);
        }
        return topicExists;
    }

    public static Boolean deleteTopic(String topic, String zookeeperUrl, ConcurrentHashMap<String, Long> cache) {
        ZkUtils zkUtils = ZookeeperUtility.newZkUtils(zookeeperUrl);

        return deleteTopic(topic, zkUtils, cache);
    }

    private static Boolean doesTopicExist(String topic, ZkUtils zkUtils, ConcurrentHashMap<String, Long> cache, boolean put) {
        if(topic == null) {
            throw new IllegalStateException(Constants.LOGGER_TOPIC_NULL_ERROR);
        }
        if(zkUtils == null) {
            throw new IllegalStateException(Constants.LOGGER_ZKUTILS_NULL_ERROR);
        }

        // First check if topic in local cache, if not add it to the cache now
        if(CacheUtility.keyExists(topic, cache, true)) {
            return true;
        } else {
            return AdminUtils.topicExists(zkUtils, topic);
        }
    }

    private static Boolean createTopicTask(String topic, ZkUtils zkUtils) {
        Boolean topicCreated;

        // Wait for topic to be created
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            executor.invokeAll(Arrays.asList(new CreateTopicTask(zkUtils, topic)), Constants.ZK_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS); // Timeout
            topicCreated = true;
        } catch (InterruptedException e) {
            topicCreated = false;
            LOGGER.warning(Constants.LOGGER_TOPIC_CREATE_WARN + topic);
        }
        executor.shutdown();

        return topicCreated;
    }


}
