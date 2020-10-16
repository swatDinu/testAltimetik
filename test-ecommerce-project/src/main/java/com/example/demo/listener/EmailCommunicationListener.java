package com.example.demo.listener;

import java.io.ByteArrayInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.domain.EmailRequest;
import com.example.demo.service.EmailService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class EmailCommunicationListener implements MessageListener {

    Logger logger = Logger.getLogger(getClass());

    @Autowired
    EmailService emailService;
    
    @Override
    public void onMessage(Message message) {

        String request = null;
        try {
            Gson gson = new GsonBuilder().create();
            request = IOUtils.toString(new ByteArrayInputStream(message.getBody()));
            logger.debug("EmailCommunicationListener-onMessage- Started processing JMS message: " + request);
            EmailRequest emailReq = gson.fromJson(request, EmailRequest.class);
            emailService.sendMail(emailReq);
        } catch (Exception e) {
            logger.error("Failed while processing message ", e);
        }

    }

}
