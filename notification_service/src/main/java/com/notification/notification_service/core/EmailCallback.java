package com.notification.notification_service.core;

@FunctionalInterface
public interface EmailCallback {
    void notificate(boolean hasErrors, String message);
}
