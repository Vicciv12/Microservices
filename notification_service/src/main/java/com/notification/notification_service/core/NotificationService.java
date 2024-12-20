package com.notification.notification_service.core;

import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;

public interface NotificationService {
    void notificateSingle(String to, String title, String message, HttpServletRequest request) throws Exception;
    void notificateSingle(String title, String message, HttpServletRequest request) throws Exception;
    void notificateAll(Set<String> to, String title, String message, HttpServletRequest request) throws Exception;
}
