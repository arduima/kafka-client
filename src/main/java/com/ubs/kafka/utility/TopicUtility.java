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

    public static Boolean createTopic(String topic, ZkUtils zkUtils) {
        Boolean topicExists = doesTopicExist(topic, zkUtils);
        Boolean topicCreated = Boolean.FALSE;
        if(!topicExists) {
            LOGGER.warning(Constants.LOGGER_CREATE_TOPIC_WARN + topic);
            AdminUtils.createTopic(zkUtils, topic, Constants.TOPIC_PARTITIONS, Constants.TOPIC_REPLICATION_FACTOR, new Properties());

            topicCreated = createTopicTask(topic, zkUtils);
        }

        return topicCreated;
    }

    public static Boolean createTopic(String topic, String zookeeperUrl) {
        ZkUtils zkUtils = ZookeeperUtility.newZkUtils(zookeeperUrl);

        return createTopic(topic, zkUtils);
    }

    public static void deleteTopic(String topic, ZkUtils zkUtils) {
        Boolean topicExists = doesTopicExist(topic, zkUtils);
        if(!topicExists) {
            AdminUtils.deleteTopic(zkUtils, topic);
        }
    }

    private static Boolean doesTopicExist(String topic, ZkUtils zkUtils) {
        if(topic == null) {
            throw new IllegalStateException(Constants.LOGGER_TOPIC_NULL_ERROR);
        }
        if(zkUtils == null) {
            throw new IllegalStateException(Constants.LOGGER_ZKUTILS_NULL_ERROR);
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
                    Thread.sleep(3000);
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

}
