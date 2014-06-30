package com.autentia.tutorial.gcm.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryDeviceDAO implements DeviceDAO {

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryDeviceDAO.class);

    private static final List<String> DEVICE_IDS = new ArrayList<>();

    @Override
    public void addDeviceId(String deviceId) {
        synchronized (DEVICE_IDS) {
            if (!DEVICE_IDS.contains(deviceId)) {
                DEVICE_IDS.add(deviceId);
                LOG.info("Device ID successfully added {}", deviceId);
            } else {
                LOG.trace("Device ID already exists {}", deviceId);
            }
        }
    }

    @Override
    public List<String> getDeviceIds() {
        synchronized (DEVICE_IDS) {
            return new ArrayList<>(DEVICE_IDS);
        }
    }

    @Override
    public void removeDeviceId(String deviceId) {
        synchronized (DEVICE_IDS) {
            if (DEVICE_IDS.contains(deviceId)) {
                DEVICE_IDS.remove(deviceId);
                LOG.info("Device ID successfully removed {}", deviceId);
            } else {
                LOG.trace("Device ID doesnt exist {}", deviceId);
            }
        }
    }
}
