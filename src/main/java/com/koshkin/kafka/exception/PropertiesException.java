package com.koshkin.kafka.exception;

/**
 * Created by de08300 on 4/1/2016.
 */
public class PropertiesException extends RuntimeException {
    public PropertiesException(String message) {
        super(message);
    }

    public PropertiesException() {
        super();
    }

    public PropertiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesException(Throwable cause) {
        super(cause);
    }
}
