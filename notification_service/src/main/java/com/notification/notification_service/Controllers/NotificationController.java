package com.notification.notification_service.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.notification.notification_service.core.NotificationService;
import com.notification.notification_service.core.RequestValidator;
import com.notification.notification_service.models.dto.MeNotificationDto;
import com.notification.notification_service.models.dto.MutipleNotificationDto;
import com.notification.notification_service.models.dto.SingleNotificationDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class NotificationController {
    
    @Autowired
    private RequestValidator requestValidator;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/single/notificate")
    public void notificateSingle(@Valid @RequestBody SingleNotificationDto dto, HttpServletRequest request, BindingResult bindingResult) throws Exception{
        requestValidator.isValidData(bindingResult);
        notificationService.notificateSingle(dto.getReceiver(), dto.getTitle(), dto.getMessage(), request);
    }

    @PostMapping("/all/notificate")
    public void notificateAll(@Valid @RequestBody MutipleNotificationDto dto, HttpServletRequest request, BindingResult bindingResult) throws Exception{
        requestValidator.isValidData(bindingResult);
        notificationService.notificateAll(dto.getReceivers(), dto.getTitle(), dto.getMessage(), request);
    }

    @PostMapping("/me/notificate")
    public void notificateSingleByMe(@Valid @RequestBody MeNotificationDto dto, HttpServletRequest request, BindingResult bindingResult) throws Exception{
        requestValidator.isValidData(bindingResult);
        notificationService.notificateSingle(dto.getTitle(), dto.getMessage(), request );
    }

}
