package com.autentia.tutorial.gcm.dao;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DeviceDAOTest {

    private static final String DEVICE_ID_1 = "776A5F0AB71II1-U563P-8891";
    private static final String DEVICE_ID_2 = "77699F0AB71EI1-U565A-6600";
    private static final String DEVICE_ID_3 = "886A5F0AB71IA1-U213P-3301";

    @Autowired
    private DeviceDAO dao;

    @After
    public void deleteAllDeviceIds() {
        dao.removeDeviceId(DEVICE_ID_1);
        dao.removeDeviceId(DEVICE_ID_2);
        dao.removeDeviceId(DEVICE_ID_3);
    }

    @Test
    public void shouldAddAndGetDeviceIdsSuccessfully() {
        List<String> deviceIds = dao.getDeviceIds();
        assertNotNull(deviceIds);
        assertTrue(deviceIds.isEmpty());

        dao.addDeviceId(DEVICE_ID_1);
        dao.addDeviceId(DEVICE_ID_1); // duplicated!!
        dao.addDeviceId(DEVICE_ID_2);
        dao.addDeviceId(DEVICE_ID_3);

        deviceIds = dao.getDeviceIds();
        assertEquals(3, deviceIds.size());
        assertTrue(deviceIds.contains(DEVICE_ID_1));
        assertTrue(deviceIds.contains(DEVICE_ID_2));
        assertTrue(deviceIds.contains(DEVICE_ID_3));
    }

    @Test
    public void shouldRemoveDeviceId() {
        dao.addDeviceId(DEVICE_ID_1);
        dao.addDeviceId(DEVICE_ID_2);

        List<String> deviceIds = dao.getDeviceIds();
        assertEquals(2, deviceIds.size());
        assertTrue(deviceIds.contains(DEVICE_ID_1));
        assertTrue(deviceIds.contains(DEVICE_ID_2));

        dao.removeDeviceId(DEVICE_ID_1);
        deviceIds = dao.getDeviceIds();
        assertEquals(1, deviceIds.size());
        assertFalse(deviceIds.contains(DEVICE_ID_1));
        assertTrue(deviceIds.contains(DEVICE_ID_2));
    }

    @Test
    public void shouldNotRemoveAnyDeviceIdIfDeviceIdDoesntExist() {
        final String UNEXISTING_DEVICE_ID = "676A5F0NC71BBA-O013P-4401";
        dao.addDeviceId(DEVICE_ID_1);

        List<String> deviceIds = dao.getDeviceIds();
        assertEquals(1, deviceIds.size());
        assertTrue(deviceIds.contains(DEVICE_ID_1));

        dao.removeDeviceId(UNEXISTING_DEVICE_ID);
        deviceIds = dao.getDeviceIds();
        assertEquals(1, deviceIds.size());
        assertTrue(deviceIds.contains(DEVICE_ID_1));

    }


}
