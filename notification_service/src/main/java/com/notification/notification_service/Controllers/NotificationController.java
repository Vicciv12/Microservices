package com.notification.notification_service.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.notification.notification_service.core.NotificationService;
import com.notification.notification_service.models.dto.MutipleNotificationDto;
import com.notification.notification_service.models.dto.SingleNotificationDto;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/single/notificate")
    public void notificateSingle(@RequestBody SingleNotificationDto dto, HttpServletRequest request){
        notificationService.notificateSingle(dto.getReceiver(), dto.getTitle(), dto.getMessage(), request);
    }

    @PostMapping("/all/notificate")
    public void notificateAll(@RequestBody MutipleNotificationDto dto, HttpServletRequest request){
        notificationService.notificateAll(dto.getReceivers(), dto.getTitle(), dto.getMessage(), request);
    }

}
