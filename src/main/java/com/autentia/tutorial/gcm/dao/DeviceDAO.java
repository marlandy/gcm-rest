package com.autentia.tutorial.gcm.dao;

import java.util.List;

public interface DeviceDAO {

    void addRegistrationId(String registrationId);

    List<String> getRegistrationds();

    void removeRegistrationId(String registrationId);

}
