package com.ubs.fcm.util

import java.util.Properties

import com.ubs.fcm.exception.FCMKafkaException
import com.ubs.fcm.kafka.config.FCMConstants
import kafka.admin.AdminUtils
import org.I0Itec.zkclient.{ZkConnection, ZkClient}
import kafka.utils.{ZkUtils}


/**
  * Created by Devaraj Jonnadula (de08698) on 1/8/2016.
  */
object TopicUtil {


  def main(args: Array[String]) {
    println(createZkClient(FCMConstants.zkHosts, FCMConstants.zkConnectionTimeout, FCMConstants.zkSessionTimeout).getChildren("/brokers/topics").toString)
    println(createTopicIfNotExist("vv", true))

  }

  def createTopicIfNotExist(topic: String, multiPartitions : Boolean): Boolean = {
    var success: Boolean = false
    var topicExists: (Boolean, ZkUtils) = null;
    try {
      topicExists = checkTopicExists(topic)
      success = topicExists._1
      if (!topicExists._1) {
        if(multiPartitions) AdminUtils.createTopic(topicExists._2, topic, FCMConstants.defaultMulPartitions, FCMConstants.defaultReplication, new Properties())
        else AdminUtils.createTopic(topicExists._2, topic, FCMConstants.defaultPartitions, FCMConstants.defaultReplication, new Properties())
        success = true
      }
    } catch {
      case e: Exception => throw new FCMKafkaException("Unable to Crete Topic :  " + topic, e)
    } finally {
      if (topicExists != null && topicExists._2 != null) topicExists._2.close()
    }
    success
  }


  def deleteTopic(topic: String): Boolean = {

    var success: Boolean = false
    var topicExists: (Boolean, ZkUtils) = null;
    try {
      topicExists = checkTopicExists(topic)
      success = topicExists._1
      if (!topicExists._1) {
        AdminUtils.deleteTopic(topicExists._2, topic)
        success = true
      }
    } catch {
      case e: Exception => throw new FCMKafkaException("Unable to Delete Topic : " + topic, e)
    } finally {
      if (topicExists != null && topicExists._2 != null) topicExists._2.close()
    }
    success
  }


  private def checkTopicExists(topic: String): (Boolean, ZkUtils) = {
    var zkUtil: ZkUtils = null

    zkUtil = ZkUtils.apply(FCMConstants.zkHosts, FCMConstants.zkSessionTimeout, FCMConstants.zkConnectionTimeout, false)
    (AdminUtils.topicExists(zkUtil, topic), zkUtil)

  }

  def topicExists(topic: String) : Boolean ={
    var zkUtil: ZkUtils = null
    var topicExists = false
    try {
      zkUtil = ZkUtils.apply(FCMConstants.zkHosts, FCMConstants.zkSessionTimeout, FCMConstants.zkConnectionTimeout, false)
      topicExists = AdminUtils.topicExists(zkUtil, topic)

    }catch{
      case e : Exception => println("Unable To check if Topic Exists for Topic ::  " + topic +e.getMessage)
    }finally {
      if(zkUtil!=null) zkUtil.close()
    }
    topicExists
  }


  private def createZkClient(zkUrl: String, sessionTimeout: Int, connectionTimeout: Int): ZkClient = {
    ZkUtils.createZkClient(zkUrl, sessionTimeout, connectionTimeout)
  }


}