package com.koshkin.kafka.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by de08300 on 4/1/2016.
 */
public final class PropertiesUtility {

    private PropertiesUtility(){}

    public static Properties readPropertyFile(String path) throws IOException {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(path);
            // load a properties file
            prop.load(input);
        }  finally {
            if (input != null) {
                input.close();
            }
        }

        return prop;
    }
}
