package com.ubs.kafka.utility;

import com.ubs.kafka.KafkaClientTest;
import kafka.utils.ZkUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dkoshkin on 4/9/16.
 */
public class ZookeeperUtilityTest extends KafkaClientTest {

    @Test
    public void newZkUtils() throws Exception {
        assertNotNull(ZookeeperUtility.newZkUtils(ZK_SERVERS));
    }

    @Test(expected = IllegalStateException.class)
    public void close() throws Exception {
        ZkUtils zkUtils = ZookeeperUtility.newZkUtils(ZK_SERVERS);
        zkUtils.close();
        zkUtils.getAllBrokersInCluster();
    }
}