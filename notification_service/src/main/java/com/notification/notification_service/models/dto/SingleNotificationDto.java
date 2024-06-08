package com.notification.notification_service.models.dto;

import lombok.Data;

@Data
public class SingleNotificationDto {
    
    private String receiver;

    private String title;

    private String message;

}
