package com.notification.notification_service.core;

@FunctionalInterface
public interface EmailServiceReactive {
    void sender(EmailServiceConfigurer configurer);
}
