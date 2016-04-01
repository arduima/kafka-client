package com.ubs.kafka.producer;

/**
 * Created by dkoshkin on 3/28/16.
 */
public enum Acknowledgements {
    ALL;

    public static String getString(Acknowledgements acknowledgementsEnum) {
        String acknowledgements = null;
        switch (acknowledgementsEnum) {
            case ALL:
                acknowledgements = "all";
                break;
        }

        return acknowledgements;
    }
}
