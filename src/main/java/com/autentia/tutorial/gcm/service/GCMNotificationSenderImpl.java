package com.autentia.tutorial.gcm.service;


import com.autentia.tutorial.gcm.api.exception.UnableToSendNotificationException;
import com.autentia.tutorial.gcm.api.model.Notification;
import com.autentia.tutorial.gcm.dao.DeviceDAO;
import com.google.android.gcm.server.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class GCMNotificationSenderImpl implements GCMNotificationSender {

    private static final Logger LOG = LoggerFactory.getLogger(GCMNotificationSenderImpl.class);

    private static final int MAX_RETRIES = 3;

    private static final int MAX_MULTICAST_SIZE = 1000;

    private static final Executor THREAD_POOL = Executors.newFixedThreadPool(5);

    private final DeviceDAO deviceDAO;

    private final Sender gcmSender;

    @Autowired
    public GCMNotificationSenderImpl(DeviceDAO deviceDAO, Sender gcmSender) {
        this.deviceDAO = deviceDAO;
        this.gcmSender = gcmSender;
    }

    @Override
    public void sendNotification(Notification notification) {
        final List<String> validDeviceIds = getValidDeviceIds(notification.getRegistrationIdsToSend());
        if (CollectionUtils.isEmpty(validDeviceIds)) {
            LOG.error("No valid devices to send notification");
            return;
        }

        if (validDeviceIds.size() == 1) {
            sendSyncNotification(notification, validDeviceIds.get(0));
        } else {
            sendAsyncNotification(notification, validDeviceIds);
        }

    }

    private List<String> getValidDeviceIds(String[] deviceIdsInNotification) {
        final List<String> registeredDeviceIds = deviceDAO.getRegistrationds();
        final List<String> validDeviceIds = new ArrayList<>();
        for (String deviceIdInNotification : deviceIdsInNotification) {
            if (registeredDeviceIds.contains(deviceIdInNotification)) {
                validDeviceIds.add(deviceIdInNotification);
            }
        }
        return validDeviceIds;
    }

    private void sendSyncNotification(Notification notification, String deviceId) {
        final Message message = createMessage(notification);
        try {
            Result result = gcmSender.send(message, deviceId, MAX_RETRIES);
            final String messageId = result.getMessageId();
            if (messageId == null) {
                LOG.error("Unable to send message {} . {}", result, message);
                throw new UnableToSendNotificationException(result.toString());
            }
            LOG.info("Message send successfully {}", result);
        } catch (IOException e) {
            LOG.error("Unable to send message to device id: {}", deviceId, e);
            throw new UnableToSendNotificationException("Internal comunication error");
        }
    }

    private void sendAsyncNotification(Notification notification, List<String> validDeviceIds) {
        final Message message = createMessage(notification);
        if (validDeviceIds.size() > MAX_MULTICAST_SIZE) {
            final List<String> deviceIdsToSend = validDeviceIds.subList(0, MAX_MULTICAST_SIZE);
            THREAD_POOL.execute(new NotificationSenderThread(message, deviceIdsToSend));
            sendAsyncNotification(notification, validDeviceIds.subList(MAX_MULTICAST_SIZE, validDeviceIds.size()));
        } else {
            THREAD_POOL.execute(new NotificationSenderThread(message, validDeviceIds));
        }
    }

    private void analyzeResult(Result result, String deviceId) {
        final String messageId = result.getMessageId();
        if (messageId != null) {
            LOG.info("Succesfully sent message to device: " + deviceId +
                    "; messageId = " + messageId);
            evalIfSameDeviceHasMoreThanOneRegistrationId(result, deviceId);
        } else {
            evalErrorCode(result, deviceId);
        }
    }

    private void evalIfSameDeviceHasMoreThanOneRegistrationId(Result result, String deviceId) {
        String canonicalRegId = result.getCanonicalRegistrationId();
        if (canonicalRegId != null) {
            LOG.info("Device with id {} has more than one registration id. Updating data ", deviceId);
            deviceDAO.removeRegistrationId(deviceId);
            deviceDAO.addRegistrationId(canonicalRegId);
        }
    }

    private void evalErrorCode(Result result, String deviceId) {
        final String error = result.getErrorCodeName();
        if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
            LOG.info("Unkown device id {}", deviceId);
            deviceDAO.removeRegistrationId(deviceId);
        } else {
            LOG.error("Error sending message to {}", deviceId, error);
        }
    }

    private Message createMessage(Notification notification) {
        final Message.Builder messageBuilder = new Message.Builder();
        messageBuilder.addData("title", notification.getTitle());
        messageBuilder.addData("message", notification.getMessage());
        messageBuilder.addData("msgcnt", notification.getBadge().toString());
        return messageBuilder.build();
    }

    private class NotificationSenderThread implements Runnable {

        private final Message message;

        private final List<String> deviceIdsToSend;

        NotificationSenderThread(Message message, List<String> deviceIdsToSend) {
            this.message = message;
            this.deviceIdsToSend = deviceIdsToSend;
        }

        public void run() {
            MulticastResult multicastResult;
            try {
                multicastResult = gcmSender.send(message, deviceIdsToSend, MAX_RETRIES);
            } catch (IOException e) {
                LOG.error("Error sending messages to GCM", e);
                return;
            }
            List<Result> results = multicastResult.getResults();
            for (int i = 0; i < deviceIdsToSend.size(); i++) {
                Result result = results.get(i);
                String deviceId = deviceIdsToSend.get(i);
                analyzeResult(result, deviceId);
            }
        }

    }
}
