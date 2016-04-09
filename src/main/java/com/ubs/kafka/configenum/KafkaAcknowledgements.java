package com.ubs.kafka.configenum;

/**
 * Created by dkoshkin on 3/28/16.
 */
public enum KafkaAcknowledgements {
    ALL, PARTIAL, NONE;

    public static String getString(KafkaAcknowledgements acknowledgementsEnum) {
        String acknowledgements = null;
        switch (acknowledgementsEnum) {
            case ALL:
                acknowledgements = "all";
                break;
            case PARTIAL:
                acknowledgements = "1";
                break;
            case NONE:
                acknowledgements = "-1";
                break;
        }

        return acknowledgements;
    }
}
