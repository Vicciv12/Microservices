package com.notification.notification_service.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.notification_service.core.EmailService;
import com.notification.notification_service.core.NotificationService;
import com.notification.notification_service.core.EmailServiceConfigurer.MessageBundle;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class NotificationServiceData implements NotificationService{

    @Autowired
    private EmailService emailService;

    @Override
    public void notificateSingle(String to, String title, String message, HttpServletRequest request) {
        salvarEmissor(request);
        emailService.sendEmail(configurer -> configurer
            .async(true)
            .messageBundle(MessageBundle.SIMPLEBUNDLE)
            .receiver(to)
            .message(message)
            .subject(title)
            .callback((hasError, $) -> {
                System.out.println($);
            })
            .send()
        );
    }

    @Override
    public void notificateAll(Set<String> to, String title, String message, HttpServletRequest request) {
        salvarEmissor(request);
        emailService.sendEmail(configurer -> configurer
            .async(true)
            .messageBundle(MessageBundle.SIMPLEBUNDLE)
            .receiver(to)
            .message(message)
            .subject(title)
            .callback((hasError, $) -> {
                System.out.println($);
            })
            .send()
        );
    }
    
    private void salvarEmissor(HttpServletRequest request){

    }

}
