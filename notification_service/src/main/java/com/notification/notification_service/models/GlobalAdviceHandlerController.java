package com.notification.notification_service.models;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.notification.notification_service.models.res.ErrorResponse;

@ControllerAdvice
public class GlobalAdviceHandlerController {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(400, e.getMessage()));
    }

}

