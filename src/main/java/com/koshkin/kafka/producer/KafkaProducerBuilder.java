package com.koshkin.kafka.producer;

import com.koshkin.kafka.serializer.KafkaSerializers;
import com.koshkin.kafka.serializer.ObjectSerializer;
import com.koshkin.kafka.utilities.CustomOption;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.List;
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

    public ServerConfiguration<K, V> newProducer() {
        return new ProducerConfiguration<>();
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
        OptionalConfiguration<K, V> acknowledgements(Acknowledgements acknowledgementsEnum);
        OptionalConfiguration<K, V> retries(Integer retries);
        OptionalConfiguration<K, V> batchSize(Integer batchedMessages);
        OptionalConfiguration<K, V> linger(Integer milliseconds);
        OptionalConfiguration<K, V> buffer(Integer bytes);

        CustomConfiguration<K, V> custom();

        SimpleProducer<K, V> build();
    }

    public interface CustomConfiguration<K, V> {
        CustomConfiguration<K, V> option(String key, Object value);

        /*Keyword to go back*/
        OptionalConfiguration<K, V> and();
    }

    private static class ProducerConfiguration<K, V> implements ServerConfiguration<K, V>, OptionalConfiguration<K, V>, CustomConfiguration<K, V>{

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
        private Integer batchedMessages;
        private Integer lingerMilliseconds;
        private Integer bufferBytes;

        private ProducerConfiguration(){}

        private List<CustomOption> customConfigurationList;

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
                    serializer = new ObjectSerializer<>();
                    break;
            }
            this.keySerializer = serializer;
            return this;
        }

        // TODO give IDE hints on valid generic values and serializers
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
                    serializer = new ObjectSerializer<>();
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
        public OptionalConfiguration<K, V> acknowledgements(Acknowledgements acknowledgementsEnum) {
            this.acknowledgements = Acknowledgements.getString(acknowledgementsEnum);
            return this;
        }

        @Override
        public OptionalConfiguration<K, V> retries(Integer retries) {
            this.retries = retries;
            return this;
        }

        @Override
        public OptionalConfiguration<K, V> batchSize(Integer batchedMessages) {
            this.batchedMessages = batchedMessages;
            return this;
        }

        @Override
        public OptionalConfiguration<K, V> linger(Integer milliseconds) {
            this.lingerMilliseconds = milliseconds;
            return this;
        }

        @Override
        public OptionalConfiguration<K, V> buffer(Integer bytes) {
            this.bufferBytes = bytes;
            return this;
        }

        /*Pass in any key value pair*/
        @Override
        public CustomConfiguration<K, V> custom() {
            return this;
        }

        @Override
        public CustomConfiguration<K, V> option(String key, Object value) {
            if(customConfigurationList == null) {
                customConfigurationList = new ArrayList<>();
            }
            customConfigurationList.add(new CustomOption(key, value));
            return this;
        }

        @Override
        public OptionalConfiguration<K, V> and() {
            return this;
        }

        @Override
        public SimpleProducer<K, V> build() {
            Properties properties = new Properties();

            /*Validation*/
            if(servers == null) {
                throw new IllegalStateException(ILLEGAL_STATE_EXCEPTION_MESSAGE_SERVER);
            }

            properties.setProperty(SERVERS, servers);

            if(acknowledgements != null) {
                properties.put(ACKNOWLEDGEMENTS, acknowledgements);
            }
            if(retries != null) {
                properties.put(RETRIES, retries);
            }
            if(batchedMessages != null) {
                properties.put(BATCHED_MESSAGES, batchedMessages);
            }
            if(lingerMilliseconds != null) {
                properties.put(LINGER_MILLISECONDS, lingerMilliseconds);
            }
            if(bufferBytes != null) {
                properties.put(BUFFER_BYTES, bufferBytes);
            }

            if(customConfigurationList != null) {
                for(CustomOption option : customConfigurationList) {
                    properties.put(option.getKey(), option.getValue());
                }
            }

            if(keySerializer == null) {
                keySerializer = (Serializer<K>) new StringSerializer();
            }
            if(valueSerializer == null) {
                valueSerializer = (Serializer<V>) new StringSerializer();
            }

            return new SimpleKafkaProducer<>(properties, keySerializer, valueSerializer);
        }
    }

}
