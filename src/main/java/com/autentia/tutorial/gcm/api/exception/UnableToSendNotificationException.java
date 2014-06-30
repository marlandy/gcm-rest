package com.autentia.tutorial.gcm.api.exception;

public class UnableToSendNotificationException extends RuntimeException {

    public UnableToSendNotificationException() {
        super("Unable to send notification");
    }

}
