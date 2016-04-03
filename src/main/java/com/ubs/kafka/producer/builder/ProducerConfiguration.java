package com.ubs.kafka.producer.builder;

import com.ubs.kafka.exception.ExceptionMessages;
import com.ubs.kafka.exception.PropertiesException;
import com.ubs.kafka.preset.KafkaAcknowledgements;
import com.ubs.kafka.preset.KafkaSerializers;
import com.ubs.kafka.preset.KafkaServers;
import com.ubs.kafka.producer.SimpleProducer;
import com.ubs.kafka.producer.builder.configuration.*;
import com.ubs.kafka.serializer.ObjectSerializer;
import com.ubs.kafka.utility.CustomOption;
import com.ubs.kafka.utility.PropertiesUtility;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by dkoshkin on 4/2/16.
 */
class ProducerConfiguration<K, V> implements ServerConfiguration<K, V>, OptionalConfiguration<K, V>, CustomConfiguration<K, V>, SASLConfiguration<K, V>, SSLConfiguration<K, V>, Build<K, V>, BuildFromFile<K, V>,NestedConfiguration<K,V> {

    private String servers;
    private Serializer<K> keySerializer;
    private Serializer<V> valueSerializer;
    private Partitioner partitioner;

    private String acks;
    private Integer bufferMemory;
    private String compressionType;
    private Integer retries;
    private Integer batchSize;
    private String clientId;
    private Integer connectionsMaxIdleMs;
    private Integer lingerMs;
    private Integer maxBlockMs;
    private Integer maxRequestSize;
    private Integer receiveBufferBytes;
    private Integer requestTimeoutMs;
    private Integer sendBufferBytes;
    private Integer timeoutMs;
    private Boolean blockOnBufferFull;
    private Integer maxInFlightRequestsPerConnection;
    private Integer metadataFetchTimeoutMs;
    private Integer metadataMaxAgeMs;
    private String metricReporters;
    private Integer metricsNumSamples;
    private Integer metricsSampleWindowMs;
    private Integer reconnectBackoffMs;
    private Integer retryBackoffMs;

    private String saslKerberosServiceName;
    private String saslKerberosKinitCmd;
    private Integer saslKerberosMinTimeBeforeRelogin;
    private Double saslKerberosTicketRenewJitter;
    private Double saslKerberosTicketRenewWindowFactor;

    private String securityProtocol;

    private String sslKeyPassword;
    private String sslKeystoreLocation;
    private String sslKeystorePassword;
    private String sslTruststoreLocation;
    private String sslTruststorePassword;
    private String sslEnabledProtocols;
    private String sslKeystoreType;
    private String sslProtocol;
    private String sslProvider;
    private String sslTruststoreType;
    private String sslCipherSuites;
    private String sslEndpointIdentificationAlgorithm;
    private String sslKeymanagerAlgorithm;
    private String sslTrustmanagerAlgorithm;

    private List<CustomOption> customConfigurationList;

    ProducerConfiguration(){}

    /* Start Required Parameters */
    @Override
    public OptionalConfiguration<K, V> servers(String serverList) {
        this.servers = serverList;
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
    public OptionalConfiguration<K, V> partitioner(Partitioner partitioner) {
        this.partitioner = partitioner;
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
        this.acks = acknowledgements;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> acknowledgements(KafkaAcknowledgements acknowledgementsEnum) {
        this.acks = KafkaAcknowledgements.getString(acknowledgementsEnum);
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> bufferMemory(Integer bytes) {
        this.bufferMemory = bytes;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> compressionType(String type) {
        this.compressionType = type;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> retries(Integer retries) {
        this.retries = retries;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> batchSize(Integer messages) {
        this.batchSize = messages;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> clientId(String id) {
        this.clientId = id;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> connectionsMaxIdle(Integer milliseconds) {
        this.connectionsMaxIdleMs = milliseconds;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> linger(Integer milliseconds) {
        this.lingerMs = milliseconds;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> maxBlock(Integer milliseconds) {
        this.maxBlockMs = milliseconds;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> maxRequestSize(Integer bytes) {
        this.maxRequestSize = bytes;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> receiveBuffer(Integer bytes) {
        this.receiveBufferBytes = bytes;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> requestTimeout(Integer milliseconds) {
        this.requestTimeoutMs = milliseconds;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> sendBuffer(Integer bytes) {
        this.sendBufferBytes = bytes;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> timeout(Integer milliseconds) {
        this.timeoutMs = milliseconds;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> blockOnBufferFull(Boolean block) {
        this.blockOnBufferFull = block;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> maxInFlightRequestsPerConnection(Integer requests) {
        this.maxInFlightRequestsPerConnection = requests;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> metadataFetchTimeout(Integer milliseconds) {
        this.metadataFetchTimeoutMs = milliseconds;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> metadataMaxAge(Integer milliseconds) {
        this.metadataMaxAgeMs = milliseconds;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> metricReporters(String reporters) {
        this.metricReporters = reporters;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> metricsNumSamples(Integer samples) {
        this.metricsNumSamples = samples;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> reconnectBackoff(Integer milliseconds) {
        this.reconnectBackoffMs = milliseconds;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> metricsSampleWindow(Integer milliseconds) {
        this.metricsSampleWindowMs = milliseconds;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> retryBackoff(Integer milliseconds) {
        this.retryBackoffMs = milliseconds;
        return this;
    }

    /* SASL configuration */
    @Override
    public SASLConfiguration<K, V> sasl() {
        return this;
    }

    @Override
    public SASLConfiguration<K, V> kerberosServiceName(String name) {
        this.saslKerberosServiceName = name;
        return this;
    }

    @Override
    public SASLConfiguration<K, V> kerberosKinitCmd(String command) {
        this.saslKerberosKinitCmd = command;
        return this;
    }

    @Override
    public SASLConfiguration<K, V> kerberosMinTimeBeforeRelogin(Integer milliseconds) {
        this.saslKerberosMinTimeBeforeRelogin = milliseconds;
        return this;
    }

    @Override
    public SASLConfiguration<K, V> kerberosTicketRenewJitter(Double percentage) {
        this.saslKerberosTicketRenewJitter = percentage;
        return this;
    }

    @Override
    public SASLConfiguration<K, V> kerberosTicketRenewWindowFactor(Double factor) {
        this.saslKerberosTicketRenewWindowFactor = factor;
        return this;
    }

    @Override
    public OptionalConfiguration<K, V> securityProtocol(String protocol) {
        this.securityProtocol = protocol;
        return this;
    }

    /* SSL configuration */
    @Override
    public SSLConfiguration<K, V> ssl() {
        return this;
    }

    @Override
    public SSLConfiguration<K, V> keyPassword(String password) {
        this.sslKeyPassword = password;
        return this;
    }

    @Override
    public SSLConfiguration<K, V> keystoreLocation(String location) {
        this.sslKeystoreLocation = location;
        return this;
    }

    @Override
    public SSLConfiguration<K, V> keystorePassword(String password) {
        this.sslKeystorePassword = password;
        return this;
    }

    @Override
    public SSLConfiguration<K, V> truststoreLocation(String location) {
        this.sslTruststoreLocation = location;
        return this;
    }

    @Override
    public SSLConfiguration<K, V> truststorePassword(String password) {
        this.sslTruststorePassword = password;
        return this;
    }

    @Override
    public SSLConfiguration<K, V> enabledProtocols(String protocolList) {
        this.sslEnabledProtocols = protocolList;
        return this;
    }

    @Override
    public SSLConfiguration<K, V> keystoreType(String type) {
        this.sslKeystoreType = type;
        return this;
    }

    @Override
    public SSLConfiguration<K, V> protocol(String protocol) {
        this.sslProtocol = protocol;
        return this;
    }

    @Override
    public SSLConfiguration<K, V> provider(String provider) {
        this.sslProvider = provider;
        return this;
    }

    @Override
    public SSLConfiguration<K, V> truststoreType(String type) {
        this.sslTruststoreType = type;
        return this;
    }

    @Override
    public SSLConfiguration<K, V> cipherSuites(String suiteList) {
        this.sslCipherSuites = suiteList;
        return this;
    }

    @Override
    public SSLConfiguration<K, V> endpointIdentificationAlgorithm(String algorithm) {
        this.sslEndpointIdentificationAlgorithm = algorithm;
        return this;
    }

    @Override
    public SSLConfiguration<K, V> keymanagerAlgorithm(String algorithm) {
        this.sslKeymanagerAlgorithm = algorithm;
        return this;
    }

    @Override
    public SSLConfiguration<K, V> trustmanagerAlgorithm(String algorithm) {
        this.sslTrustmanagerAlgorithm = algorithm;
        return this;
    }

    /* Pass in any key value pair */
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

    /* Build SimpleProducer */
    @Override
    public SimpleProducer<K, V> build() {
        Properties properties = new Properties();

        /* Validation */
        if(servers == null) {
            throw new IllegalStateException(ExceptionMessages.ILLEGAL_STATE_EXCEPTION_MESSAGE_SERVER);
        }

        /* Get the full class name */
        if(partitioner != null) {
            put(properties, "partitioner.class", partitioner.getClass().getCanonicalName());
        }

        /* Add all fields to Properties */
        put(properties, "bootstrap.servers", servers);
        put(properties, "acks", acks);
        put(properties, "bufferMemory", bufferMemory);
        put(properties, "compressionType", compressionType);
        put(properties, "retries", retries);
        put(properties, "batchSize", batchSize);
        put(properties, "clientId", clientId);
        put(properties, "connectionsMaxIdleMs", connectionsMaxIdleMs);
        put(properties, "lingerMs", lingerMs);
        put(properties, "maxBlockMs", maxBlockMs);
        put(properties, "maxRequestSize", maxRequestSize);
        put(properties, "receiveBufferBytes", receiveBufferBytes);
        put(properties, "requestTimeoutMs", requestTimeoutMs);
        put(properties, "sendBufferBytes", sendBufferBytes);
        put(properties, "timeoutMs", timeoutMs);
        put(properties, "blockOnBufferFull", blockOnBufferFull);
        put(properties, "maxInFlightRequestsPerConnection", maxInFlightRequestsPerConnection);
        put(properties, "metadataFetchTimeoutMs", metadataFetchTimeoutMs);
        put(properties, "metadataMaxAgeMs", metadataMaxAgeMs);
        put(properties, "metricReporters", metricReporters);
        put(properties, "metricsNumSamples", metricsNumSamples);
        put(properties, "metricsSampleWindowMs", metricsSampleWindowMs);
        put(properties, "reconnectBackoffMs", reconnectBackoffMs);
        put(properties, "retryBackoffMs", retryBackoffMs);

        put(properties, "saslKerberosServiceName", saslKerberosServiceName);
        put(properties, "saslKerberosKinitCmd", saslKerberosKinitCmd);
        put(properties, "saslKerberosMinTimeBeforeRelogin", saslKerberosMinTimeBeforeRelogin);
        put(properties, "saslKerberosTicketRenewJitter", saslKerberosTicketRenewJitter);
        put(properties, "saslKerberosTicketRenewWindowFactor", saslKerberosTicketRenewWindowFactor);

        put(properties, "securityProtocol", securityProtocol);

        put(properties, "sslKeyPassword", sslKeyPassword);
        put(properties, "sslKeystoreLocation", sslKeystoreLocation);
        put(properties, "sslKeystorePassword", sslKeystorePassword);
        put(properties, "sslTruststoreLocation", sslTruststoreLocation);
        put(properties, "sslTruststorePassword", sslTruststorePassword);
        put(properties, "sslEnabledProtocols", sslEnabledProtocols);
        put(properties, "sslKeystoreType", sslKeystoreType);
        put(properties, "sslProtocol", sslProtocol);
        put(properties, "sslProvider", sslProvider);
        put(properties, "sslTruststoreType", sslTruststoreType);
        put(properties, "sslCipherSuites", sslCipherSuites);
        put(properties, "sslEndpointIdentificationAlgorithm", sslEndpointIdentificationAlgorithm);
        put(properties, "sslKeymanagerAlgorithm", sslKeymanagerAlgorithm);
        put(properties, "sslTrustmanagerAlgorithm", sslTrustmanagerAlgorithm);

        /* Add custom fields to Properties */
        if(customConfigurationList != null) {
            for(CustomOption option : customConfigurationList) {
                properties.put(option.getKey(), option.getValue());
            }
        }

        /* Add default serializers */
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

    private void put(Properties properties,  String key, Object value) {
        if(value != null) {
            properties.put(key, value);
        }
    }

}
