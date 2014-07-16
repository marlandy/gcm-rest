package com.autentia.tutorial.gcm.api.exception;

public class UnableToSendNotificationException extends RuntimeException {

    public UnableToSendNotificationException() {
        super("Unable to send notification");
    }

    public UnableToSendNotificationException(String reason) {
        super("Unable to send notification. Reason: " + reason);
    }

}
