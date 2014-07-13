package com.autentia.tutorial.gcm.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryDeviceDAO implements DeviceDAO {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryDeviceDAO.class);

    private static final List<String> REGISTRATION_IDS = new ArrayList<>();

    @Override
    public void addRegistrationId(String registrationId) {
        synchronized (REGISTRATION_IDS) {
            if (!REGISTRATION_IDS.contains(registrationId)) {
                REGISTRATION_IDS.add(registrationId);
                LOG.info("Registration ID successfully added {}", registrationId);
            } else {
                LOG.trace("Registration ID already exists {}", registrationId);
            }
        }
    }

    @Override
    public List<String> getRegistrationds() {
        synchronized (REGISTRATION_IDS) {
            return new ArrayList<>(REGISTRATION_IDS);
        }
    }

    @Override
    public void removeRegistrationId(String registrationId) {
        synchronized (REGISTRATION_IDS) {
            if (REGISTRATION_IDS.contains(registrationId)) {
                REGISTRATION_IDS.remove(registrationId);
                LOG.info("Registration ID successfully removed {}", registrationId);
            } else {
                LOG.trace("Registration ID doesnt exist {}", registrationId);
            }
        }
    }
}
