package com.notification.notification_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.notification.notification_service.core.EmailService;
import com.notification.notification_service.core.EmailServiceReactive;

@Service
public class EmailServiceData implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(EmailServiceReactive service){
        service.sender(new EmailServiceConfigurerData(javaMailSender));
    }

}
