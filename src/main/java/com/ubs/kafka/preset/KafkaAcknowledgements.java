package com.ubs.kafka.preset;

/**
 * Created by dkoshkin on 3/28/16.
 */
public enum KafkaAcknowledgements {
    ALL;

    public static String getString(KafkaAcknowledgements acknowledgementsEnum) {
        String acknowledgements = null;
        switch (acknowledgementsEnum) {
            case ALL:
                acknowledgements = "all";
                break;
        }

        return acknowledgements;
    }
}
