package com.ubs.kafka.utility;

import kafka.admin.AdminUtils;
import kafka.utils.ZkUtils;

import java.util.logging.Logger;

/**
 * Created by dkoshkin on 4/4/16.
 */
public class TopicUtility {

    public static final Logger LOGGER = Logger.getLogger(TopicUtility.class.getName());

    private TopicUtility() {}

    public static Boolean createTopicIfNotExist(String topic, ZkUtils zkUtils) {
        Boolean topicExists = doesTopicExist(topic, zkUtils);
        if(topicExists != null && !topicExists) {
            LOGGER.warning(Constants.LOGGER_CREATE_TOPIC_WARN + topic);
            AdminUtils.createTopic(zkUtils, topic, Constants.TOPIC_PARTITIONS, Constants.TOPIC_REPLICATION_FACTOR, null);
            topicExists = true;
        }

        return topicExists;
    }

    public static void deleteTopic(String topic, ZkUtils zkUtils) {
        Boolean topicExists = doesTopicExist(topic, zkUtils);
        if(topicExists != null && !topicExists) {
            AdminUtils.deleteTopic(zkUtils, topic);
        }
    }

    private static Boolean doesTopicExist(String topic, ZkUtils zkUtils) {
        if(topic == null) {
            LOGGER.warning(Constants.LOGGER_TOPIC_NULL_WARN);
        }
        if(zkUtils == null) {
            LOGGER.warning(Constants.LOGGER_ZKUTILS_NULL_WARN);
        }
        if(topic != null && zkUtils != null) {
            return AdminUtils.topicExists(zkUtils, topic);
        }
        return null;
    }

}
