package com.notification.notification_service.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import com.notification.notification_service.core.RequestValidator;

@Service
public class ValidationRequestService implements RequestValidator{
    
    @Override
    public void isValidData(BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            bindingResult.getFieldErrors().forEach(e -> {
                errors.append(e.getDefaultMessage());
                errors.append("\n");
            });
            throw new Exception(errors.toString());
        }
    }
}
