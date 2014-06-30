package com.autentia.tutorial.gcm.service;

import com.autentia.tutorial.gcm.api.model.Notification;

public interface GCMNotificationSender {

    void sendNotification(Notification notification);

}
