package com.ubs.kafka.utility;

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
        Boolean topicExists = doesTopicExist(topic, zkUtils, cache, false);
        if(topicExists) {
            AdminUtils.deleteTopic(zkUtils, topic);
            deleteTopicFromCache(topic, cache);
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

        // First check if topic in local cache, if not add now
        if(topicExistsInCache(topic, cache, true)) {
            return true;
        }
        return AdminUtils.topicExists(zkUtils, topic);
    }

    private static Boolean createTopicTask(String topic, ZkUtils zkUtils) {
        Boolean topicCreated;
        // Wait for topic to be created
        class CreateTopicTask implements Callable<Boolean> {
            ZkUtils zkUtils;
            String topic;

            public CreateTopicTask(ZkUtils zkUtils, String topic) {
                this.zkUtils = zkUtils;
                this.topic = topic;
            }

            @Override
            public Boolean call() {
                try {
                    // TODO find better way to see if topic is ready
                    Thread.sleep(Constants.CREATE_TOPIC_TIMEOUT);
                } catch (InterruptedException e) {}
                return true;
            }
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();

        try {
            executor.invokeAll(Arrays.asList(new CreateTopicTask(zkUtils, topic)), Constants.ZK_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS); // Timeout
            topicCreated = true;
        } catch (InterruptedException e) {
            topicCreated = false;
            LOGGER.warning(Constants.LOGGER_TOPIC_CREATE_WARN + topic);
        }
        executor.shutdown();
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return topicCreated;
    }

    // If putIfAbsent() doesn't return null, the key is in Map
    // TODO fix error when using Map
    private static Boolean topicExistsInCache(String topic, ConcurrentHashMap<String, Long> cache, boolean put) {
        if(cache == null || topic == null) {
            return false;
        }
        // Auto create key, value
        if(put) {
            return cache.putIfAbsent(topic, new Long(System.currentTimeMillis())) != null;
        }
        else {
            return cache.get(topic) != null;
        }
    }

    private static void deleteTopicFromCache(String topic, ConcurrentHashMap<String, Long> cache) {
        if(cache == null || topic == null) {
            return;
        }
        cache.remove(topic);
    }

}
