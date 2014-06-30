package com.autentia.tutorial.gcm.dao;

import java.util.List;

public interface DeviceDAO {

    void addDeviceId(String deviceId);

    List<String> getDeviceIds();

    void removeDeviceId(String deviceId);

}
