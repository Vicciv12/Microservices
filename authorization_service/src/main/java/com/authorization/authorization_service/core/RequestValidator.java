package com.authorization.authorization_service.core;

import org.springframework.validation.BindingResult;

public interface RequestValidator {
    void isValidData(BindingResult bindingResult) throws Exception;
}