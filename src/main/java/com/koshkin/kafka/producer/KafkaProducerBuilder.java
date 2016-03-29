package com.koshkin.kafka.producer;

import com.koshkin.kafka.serializer.KafkaSerializers;
import com.koshkin.kafka.serializer.ObjectSerializer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * Created by dkoshkin
 * KafkaProducerBuilder using the step building pattern
 * Must be instantiated to be handle generic parameters
 * Required Parameters: _Configuration with a method returning a different _Configuration
 * Optional Parameters: a method in OptionalConfiguration that returns OptionalConfiguration
 * TODO add documentation
 */
public class KafkaProducerBuilder<K, V> {
    public KafkaProducerBuilder(){}

    public ServerConfiguration<K, V> newBuilder() {
        return new ProducerConfiguration<K, V>();
    }

    public interface ServerConfiguration<K, V> {
        OptionalConfiguration<K, V> servers(String servers);
    }

    public interface OptionalConfiguration<K, V> {
        OptionalConfiguration<K, V> keySerializer(Serializer<K> keySerializer);
        OptionalConfiguration<K, V> keySerializer(KafkaSerializers serializerEnum);
        OptionalConfiguration<K, V> valueSerializer(Serializer<V> valueSerializer);
        OptionalConfiguration<K, V> valueSerializer(KafkaSerializers serializerEnum);
        OptionalConfiguration<K, V> acknowledgements(String acknowledgements);
        OptionalConfiguration<K, V> retries(Integer retries);
        OptionalConfiguration<K, V> batchSize(Long batchedMessages);
        OptionalConfiguration<K, V> linger(Integer milliseconds);
        OptionalConfiguration<K, V> buffer(Long bytes);

        Producer<K, V> build();
    }

    public interface BuildConfiguration<K, V> {
        Producer<K, V> build();
    }

    public static class ProducerConfiguration<K, V> implements ServerConfiguration<K, V>, OptionalConfiguration<K, V>{

        private final static String ILLEGAL_STATE_EXCEPTION_MESSAGE_SERVER = "Servers cannot be empty";
        private final static String SERVERS = "bootstrap.servers";
        private final static String ACKNOWLEDGEMENTS = "acknowledgements";
        private final static String RETRIES = "retries";
        private final static String BATCHED_MESSAGES = "batch.size";
        private final static String LINGER_MILLISECONDS = "linger.ms";
        private final static String BUFFER_BYTES =  "buffer.memory";

        private String servers;
        private Serializer<K> keySerializer;
        private Serializer<V> valueSerializer;
        private String acknowledgements;
        private Integer retries;
        private Long batchedMessages;
        private Integer lingerMilliseconds;
        private Long bufferBytes;

        /* Start Required Parameters */

        @Override
        public OptionalConfiguration<K, V> servers(String servers) {
            this.servers = servers;
            return this;
        }

        /* End Required Parameters*/

        @Override
        public OptionalConfiguration<K, V> keySerializer(Serializer<K> keySerializer) {
            this.keySerializer = keySerializer;
            return this;
        }

        @Override
        public OptionalConfiguration<K, V> valueSerializer(Serializer<V> valueSerializer) {
            this.valueSerializer = valueSerializer;
            return this;
        }

        @Override
        public OptionalConfiguration<K, V> keySerializer(KafkaSerializers serializerEnum) {
            Serializer<K> serializer = null;
            switch (serializerEnum) {
                case STRING:
                    serializer = (Serializer<K>) new StringSerializer();
                    break;
                case BYTEARRAY:
                    serializer = (Serializer<K>) new ByteArraySerializer();
                    break;
                case OBJECT:
                    serializer = new ObjectSerializer<K>();
                    break;
            }
            this.keySerializer = serializer;
            return this;
        }

        @Override
        public OptionalConfiguration<K, V> valueSerializer(KafkaSerializers serializerEnum) {
            Serializer<V> serializer = null;
            switch (serializerEnum) {
                case STRING:
                    serializer = (Serializer<V>) new StringSerializer();
                    break;
                case BYTEARRAY:
                    serializer = (Serializer<V>) new ByteArraySerializer();
                    break;
                case OBJECT:
                    serializer = new ObjectSerializer<V>();
                    break;
            }
            this.valueSerializer = serializer;
            return this;
        }

        @Override
        public OptionalConfiguration<K, V> acknowledgements(String acknowledgements) {
            this.acknowledgements = acknowledgements;
            return this;
        }

        @Override
        public OptionalConfiguration<K, V> retries(Integer retries) {
            this.retries = retries;
            return this;
        }

        @Override
        public OptionalConfiguration<K, V> batchSize(Long batchedMessages) {
            this.batchedMessages = batchedMessages;
            return this;
        }

        @Override
        public OptionalConfiguration<K, V> linger(Integer milliseconds) {
            this.lingerMilliseconds = milliseconds;
            return this;
        }

        @Override
        public OptionalConfiguration<K, V> buffer(Long bytes) {
            this.bufferBytes = bytes;
            return this;
        }

        @Override
        public Producer<K, V> build() {
            Properties properties = new Properties();

            /*Validation*/
            if(servers == null) {
                throw new IllegalStateException(ILLEGAL_STATE_EXCEPTION_MESSAGE_SERVER);
            }

            properties.setProperty(SERVERS, servers);

            if(servers != null) {
                properties.put(ACKNOWLEDGEMENTS, acknowledgements);
            }
            if(servers != null) {
                properties.put(RETRIES, retries);
            }
            if(servers != null) {
                properties.put(BATCHED_MESSAGES, batchedMessages);
            }
            if(servers != null) {
                properties.put(LINGER_MILLISECONDS, lingerMilliseconds);
            }
            if(servers != null) {
                properties.put(BUFFER_BYTES, bufferBytes);
            }

            if(keySerializer == null) {
                keySerializer = (Serializer<K>) new StringSerializer();
            }
            if(valueSerializer == null) {
                valueSerializer = (Serializer<V>) new StringSerializer();
            }

            return new SimpleKafkaProducer<K, V>(properties, keySerializer, valueSerializer);
        }
    }

}
