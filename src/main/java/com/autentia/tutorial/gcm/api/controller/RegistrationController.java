package com.autentia.tutorial.gcm.api.controller;

import com.autentia.tutorial.gcm.dao.DeviceDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegistrationController {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private final DeviceDAO deviceDAO;

    @Autowired
    public RegistrationController(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    @RequestMapping(value = "/registrations", method = RequestMethod.POST)
    public @ResponseBody HttpEntity<String> sendNotification(@RequestParam String registrationId) {
        LOG.trace("Registering id {}", registrationId);
        deviceDAO.addRegistrationId(registrationId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
