package com.autentia.tutorial.gcm.api.controller;

import com.autentia.tutorial.gcm.api.exception.InvalidInputDataException;
import com.autentia.tutorial.gcm.api.model.Notification;
import com.autentia.tutorial.gcm.service.GCMNotificationSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotificationController {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationController.class);

    private final GCMNotificationSender gcmNotificationSender;

    @Autowired
    public NotificationController(GCMNotificationSender gcmNotificationSender) {
        this.gcmNotificationSender = gcmNotificationSender;
    }

    @RequestMapping(value = "/notifications", method = RequestMethod.POST)
    public @ResponseBody HttpEntity<Notification> sendNotification(@RequestBody Notification notification) {
        LOG.trace("Sending notification {}", notification);
        validateNotificationData(notification);
        gcmNotificationSender.sendNotification(notification);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    private void validateNotificationData(Notification notification) {
        validateBadge(notification.getBadge());
        validateTittle(notification.getTitle());
        validateMessage(notification.getMessage());
        validateDeviceIdsToSend(notification.getRegistrationIdsToSend());
    }

    private void validateBadge(Integer badge) {
        if (badge == null) {
            throwError("Notification badge is required");
        } else if (badge < 1) {
            throwError("Notification badge must be greater than 0");
        }
    }

    private void validateTittle(String title) {
        if (title == null) {
            throwError("Notification title is required");
        }
    }

    private void validateMessage(String message) {
        if (message == null) {
            throwError("Notification message is required");
        }
    }

    private void validateDeviceIdsToSend(String[] deviceIdsToSend) {
        if (deviceIdsToSend == null || deviceIdsToSend.length == 0) {
            throwError("Notification device ids to send is required");
        }
    }

    private void throwError(String message) {
        LOG.warn(message);
        throw new InvalidInputDataException(message);
    }



}
