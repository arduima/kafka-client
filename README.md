# Kafka Client
###TL;DR
```JAVA
//Producer : Note that because of <String, String> both the message key and value must be Strings
Producer<String, String> producer = new KafkaProducerBuilder<String, String>().newBuilder()
        .servers("{kafkahost}:{port#}")
        .build();
producer.send(new ProducerRecord<String, String>("my-awesome-topic", "somekey", "somevalue"));
  
//Consumer TODO
```
---
## Producer
SimpleKafkaProducer is a wrapper around the [KafkaProducer](https://kafka.apache.org/090/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html) that provides an elegant builder style API for an easy setup.

**Basic Example:**
```JAVA
Producer<K, V> producer = new KafkaProducerBuilder<K, V>().newBuilder()
        .servers("{kafkahost}:{port#}")
        .build();
producer.send(new ProducerRecord<K, V>("my-awesome-topic", "somekey", "somevalue"));
```
`.servers()` is **required** and accepts a String of comma separated of **host-ip:port** ie: `"10.0.0.10:9000,10.0.0.11:9000"`. Although only 1 host is required and the rest of the hosts in the cluster can be resolved, it is a good idea to put multiple IPs in case any of the hosts become unavailable.

**Full Example:**
```JAVA
Producer<String, MyCustomObject> producer = new KafkaProducerBuilder<String, MyCustomObject>().newBuilder()
        .servers("{kafkahost}:{port#}")
        .keySerializer(KafkaSerializers.STRING)
        .valueSerializer(KafkaSerializers.OBJECT)
        .acknowledgements(Acknowledgements.ALL)
        .retries(0)
        .batchSize(16384)
        .buffer(102400)
        .linger(1)
        .custom()
            .option("some.option", "validValue")
            .option("another.option", 1)
            .and()
        .build();
  producer.send(new ProducerRecord<String, MyCustomObject>(
                                                  "my-awesome-topic", 
                                                  "somekey", 
                                                  new MyCustomObject("Something", "Goes", "Here", 1)
                                                );
```
* `.servers(String)`
* `.keySerializer(KafkaSerializersEnum)` See [Serializers](#serializers)
* `.valueSerializer(KafkaSerializers.OBJECT)` See [Serializers](#serializers)
* `.acknowledgements(AcknowledgementsEnum)` controls the criteria under which requests are considered complete. The *ALL* setting we have specified will result in blocking on the full commit of the record, the slowest but most durable setting.
* `.retries(int)` number of retries the producer will perform if message fails. Enabling retries also opens up the possibility of duplicates (see the documentation on message delivery [semantics](http://kafka.apache.org/documentation.html#semantics) for details)]
* `.batchSize(int)` number of messages batched on the producer. 
* `.buffer(int)` buffer size in bytes for messages waiting to be sent.
* `.linger(int)` the time in ms to wait to send messages. This will create a latency, however it will be reduce network traffic and create fewer more efficient requests. 
* `.custom().option(Object, Object).and()` allows to provide any number of custom key value pairs to the producer. Calling `.and()` after all the `.option()` are provided goes back to building the producer.

`.build()` creates a new instance of `Producer<K, V>` where `K` is the message key type and `V` is the message value type. In this example we used `KafkaSerializers.STRING` for the key, and `KafkaSerializers.OBJECT` for the value as the serializers, so the instantiation must also reflect that. ie: `Producer<String, MyCustomObject>` and `new ProducerRecord<String, MyCustomObject>`
See [Serializers](#serializers) for full details.


## <a name="serializers"></a>Serializers
The default is `StringSerializer`

The library provives 3 Serializer types:
* `StringSerializer` `KafkaSerializers.STRING`
* `ByteArraySerializer` `KafkaSerializers.BYTEARRAY`
* `ObjectSerializer<T>` `KafkaSerializers.OBJECT`, where `T` is a POJO. **Note** all the members of the object must be Serializable. This object will be internally converted and sent as a `byte[]`

You can also provide your own serializer by implementing `org.apache.kafka.common.serialization.Serializer<T>` and `org.apache.kafka.common.serialization.Deserializer<T>`. See `ObjectSerializer.java` implementation for an example.

`new Producer<K, V>` and `new ProducerRecord<K, V>` must be compatible with the serializers specified.


