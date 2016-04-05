package com.ubs.kafka.utility;

import kafka.utils.ZkUtils;

/**
 * Created by dkoshkin on 4/4/16.
 */
public class ZookeeperUtility {

    private ZookeeperUtility() {}

    public static ZkUtils newZkUtils(String url) {
        return ZkUtils.apply(
                url,
                Constants.ZK_SESSION_TIMEOUT,
                Constants.ZK_CONNECTION_TIMEOUT,
                Constants.ZK_IS_SECURITY_ENABLED);
    }

    public static void close(ZkUtils zkUtils) {
        if(zkUtils != null) {
            zkUtils.close();
        }
    }
}
