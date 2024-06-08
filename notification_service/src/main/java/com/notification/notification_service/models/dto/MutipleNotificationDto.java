package com.notification.notification_service.models.dto;

import java.util.Set;
import lombok.Data;

@Data
public class MutipleNotificationDto {
    
    private Set<String> receivers;

    private String title;

    private String message;

}
