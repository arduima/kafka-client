package com.ubs.kafka.task;

import com.ubs.kafka.utility.Constants;
import kafka.utils.ZkUtils;

import java.util.concurrent.Callable;

/**
 * Created by dkoshkin on 4/11/16.
 */
public class CreateTopicTask implements Callable<Boolean> {
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