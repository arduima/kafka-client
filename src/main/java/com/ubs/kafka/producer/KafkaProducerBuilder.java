package com.ubs.kafka.producer;

import com.ubs.kafka.exception.ExceptionMessages;
import com.ubs.kafka.exception.PropertiesException;
import com.ubs.kafka.preset.KafkaAcknowledgements;
import com.ubs.kafka.preset.KafkaSerializers;
import com.ubs.kafka.serializer.ObjectSerializer;
import com.ubs.kafka.utility.CustomOption;
import com.ubs.kafka.preset.KafkaServers;
import com.ubs.kafka.utility.PropertiesUtility;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
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

    public SimpleProducer<K, V> newProducerFromFile(String path) {
        return new ProducerConfiguration<K, V>().build(path);
    }

    public interface ServerConfiguration<K, V> {
        OptionalConfiguration<K, V> servers(String servers);
        OptionalConfiguration<K, V> servers(KafkaServers serversEnum);
    }

    public interface OptionalConfiguration<K, V> extends Build<K, V>, SerializerConfiguration<K, V> {
        OptionalConfiguration<K, V> valueSerializer(Serializer<V> valueSerializer);
        OptionalConfiguration<K, V> valueSerializer(KafkaSerializers serializerEnum);
        OptionalConfiguration<K, V> acknowledgements(String acknowledgements);
        OptionalConfiguration<K, V> acknowledgements(KafkaAcknowledgements acknowledgementsEnum);
        OptionalConfiguration<K, V> retries(Integer retries);
        OptionalConfiguration<K, V> batchSize(Integer batchedMessages);
        OptionalConfiguration<K, V> linger(Integer milliseconds);
        OptionalConfiguration<K, V> buffer(Integer bytes);

        CustomConfiguration<K, V> custom();
    }

    public interface SerializerConfiguration<K, V> {
        OptionalConfiguration<K, V> keySerializer(Serializer<K> keySerializer);
        OptionalConfiguration<K, V> keySerializer(KafkaSerializers serializerEnum);
    }

    public interface CustomConfiguration<K, V> {
        CustomConfiguration<K, V> option(String key, Object value);

        /*Keyword to go back*/
        OptionalConfiguration<K, V> and();
    }

    private interface Build<K, V> {
        SimpleProducer<K, V> build();
    }

    private interface BuildFromFile<K, V> {
        SimpleProducer<K, V> build(String path);
    }

    private static class ProducerConfiguration<K, V> implements ServerConfiguration<K, V>, OptionalConfiguration<K, V>, CustomConfiguration<K, V>, BuildFromFile<K, V>{

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

        @Override
        public OptionalConfiguration<K, V> servers(KafkaServers serversEnum) {
            // TODO need to call an SP/Service to get server urls
            this.servers = "";
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
        public OptionalConfiguration<K, V> acknowledgements(KafkaAcknowledgements acknowledgementsEnum) {
            this.acknowledgements = KafkaAcknowledgements.getString(acknowledgementsEnum);
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

        @Override
        public SimpleProducer<K, V> build(String path) {
            if(path == null) {
                throw new NullPointerException(ExceptionMessages.PROPERTIES_PATH_NULL);
            }
            Properties properties;
            try {
                properties = PropertiesUtility.readPropertyFile(path);
            } catch (IOException e) {
                throw new PropertiesException(e);
            }

            return new SimpleKafkaProducer<>(properties);
        }

    }

}
