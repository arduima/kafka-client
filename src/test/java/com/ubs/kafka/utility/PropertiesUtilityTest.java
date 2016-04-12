package com.ubs.kafka.utility;

import com.ubs.kafka.KafkaClientTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dkoshkin on 4/9/16.
 */
public class PropertiesUtilityTest extends KafkaClientTest {

    @Test
    public void readPropertyFile() throws Exception {
        assertNotNull(PropertiesUtility.readPropertyFile(PROPERTIES_FILE));
    }
}