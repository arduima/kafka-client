# Kafka Client
### TL;DR
```JAVA
// PRODUCER
// Create a SimpleProducer to send messages to an Apache Kafka server
// Note that because of <String, String> both the message key and value must be of String type
SimpleProducer<String, String> producer = new KafkaProducerBuilder<String, String>().newProducer()
        .servers("{kafkahost}:{port#}")
        .zookeeperServers("{zookeeperhost}:{port#}")
        .build();
// Send a message to the server
producer.send("my-awesome-topic", "somekey", "somevalue");
// Close the producer or use the try-with-resource statement instead
producer.close();

//CONSUMER TODO
```

### Docker Kafka Server
* For testing and development the easiest way to setup a Kafka server is to use *docker-compose*
* To deploy make sure docker and docker-compose is installed and run `./docker/start.sh`.
**Note** don't forget to change the Kakfa and ZooKeeper IPs to reflect your Kafka ip in `KafkaClientTest` and `kafka-test.properties` before running unit tests.
* To learn more about running a Kafka server in Docker go to `/docker` in this repo

---
## Producer
SimpleKafkaProducer is a wrapper around the [KafkaProducer](https://kafka.apache.org/090/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html) that provides an elegant builder style API for an easy setup.

#### Basic Example:
```JAVA
SimpleProducer<K, V> producer = new KafkaProducerBuilder<K, V>().newProducer()
        .servers("{kafkahost}:{port#}")
        .zookeeperServers("{zookeeperhost}:{port#}")
        .keySerializer(KafkaSerializers.STRING)
        .valueSerializer(KafkaSerializers.OBJECT)
        .build();
producer.send("my-awesome-topic", "somekey", new MyObject("Something", "Goes", "Here", 1));
producer.close();
```

* `.servers(String)` is **required** and accepts a *String* of comma separated of **host-ip:port** ie: `"10.0.0.10:9000,10.0.0.11:9000"`. Although only 1 host is required and the rest of the hosts in the cluster can be resolved, it is a good idea to put multiple IPs in case any of the hosts become unavailable.
* `.zookeeperServers(String) [zookeeper.servers]` if provided, allows to use zookeeper utils to manage topics
* `.keySerializer(com.ubs.kafka.configenum.KafkaSerializers)` See [Serializers](#serializers)
* `.valueSerializer(com.ubs.kafka.configenum.KafkaSerializers)` See [Serializers](#serializers)
* `.build()` creates a new instance of `Producer<K, V>` where `K` is the message key type and `V` is the message value type. In this example `KafkaSerializers.STRING` was used for the key, and `KafkaSerializers.OBJECT` was used for the value as the serializers.

**For a full list of variables see the official [Kafka Documentation](http://kafka.apache.org/documentation.html#producerconfigs)**

#### AutoCloseable Example:
```JAVA
try(SimpleProducer<String, String> producer = newProducer()) {
    // Application logic using SimpleProducer to send Kafka messages
}

private SimpleProducer<String, String> newProducer() {
    SimpleProducer<String, String> producer = new KafkaProducerBuilder<String, String>().newProducer()
            .servers("{kafkahost}:{port#}")
            .zookeeperServers("{zookeeperhost}:{port#}")
            .build();
}
```
SimpleProducer implements the `AutoCloseable` interface, use *try-with-resources* statement or call the `.close()` method on the producer.

#### Property File Example:
```JAVA
SimpleProducer<String, String> producer = new KafkaProducerBuilder<String, String>().newProducerFromFile("{path-to.properties}");
producer.send("my-awesome-topic", "somekey", "somevalue");
producer.close();
```
To see a full list of all available options look at `src/test/resources/kafka-test.properties` in this repository

### <a name="serializers"></a>Create/Delete a Topic
The client automatically creates a topic if it does not exist, is also exposes a method to create it manually:
* `TopicUtility.createTopic("my-awesome-topic", "{zookeeperhost}:{port#}");`

It is possible to delete a topic by calling:
* `producer.deleteTopic("my-awesome-topic");` or
* `TopicUtility.deleteTopic("my-awesome-topic", "{zookeeperhost}:{port#}");`

**Note** `delete.topic.enable` must be set to **true** on the Kafka server, it's default is **false**.

### Send a Message
There are a multiple methods to send a message:
```JAVA
Future<RecordMetadata> send(String topic, Integer partition, K key, V value);
Future<RecordMetadata> send(String topic, K key, V value);
Future<RecordMetadata> send(String topic, V value);
Future<RecordMetadata> send(String topic, Integer partition, K key, V value, Callback callback);
Future<RecordMetadata> send(String topic, K key, V value, Callback callback);
Future<RecordMetadata> send(String topic, V value,  Callback callback);
```
**Partitioner**
* If a partition is specified in the record, use it
* If `kafka.producer.Partitioner` is specified with a class implementing `kafka.producer.Partitioner`, use it to partition
* If no partition is specified but a key is present choose a partition based on a hash of the key [DefaultPartitioner](https://apache.googlesource.com/kafka/+/trunk/clients/src/main/java/org/apache/kafka/clients/producer/internals/DefaultPartitioner.java)
* If no partition or key is present choose a partition in a round-robin fashion

**Callback**
* This method will be called when the record sent to the server has been acknowledged.
* **Note** will only work with `acks`|`.acknowledgements()` when set to `KafkaAcknowledgements.ALL` or `KafkaAcknowledgements.PARTIAL`.
* Provide an instance of a class implementing `org.apache.kafka.clients.producer.Callback` in the callback parameter.


## <a name="serializers"></a>Serializers
The default is `StringSerializer`

The library provives 3 Serializer types:

Enum Type | Class Name
------------ | -------------
`KafkaSerializers.STRING` | `org.apache.kafka.common.serialization.StringSerializer`
`KafkaSerializers.BYTEARRAY` | `org.apache.kafka.common.serialization.ByteArraySerializer`
`KafkaSerializers.OBJECT` | `com.ubs.kafka.serializer.ObjectSerializer<T>`

Where `T` is a POJO.
**Note** all the members of the object must be Serializable. This object will be internally converted and sent as a `byte[]`

You can also provide your own serializer by implementing `org.apache.kafka.common.serialization.Serializer<T>` and `org.apache.kafka.common.serialization.Deserializer<T>`. See `ObjectSerializer.java` implementation for an example.
