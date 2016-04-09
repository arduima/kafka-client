package com.ubs.kafka.utility;

import com.ubs.kafka.KafkaClinetTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dkoshkin on 4/9/16.
 */
public class PropertiesUtilityTest extends KafkaClinetTest {

    @Test
    public void readPropertyFile() throws Exception {
        assertNotNull(PropertiesUtility.readPropertyFile(PROPERTIES_FILE));
    }
}