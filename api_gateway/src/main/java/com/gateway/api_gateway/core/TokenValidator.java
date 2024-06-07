package com.gateway.api_gateway.core;

import com.gateway.api_gateway.models.exceptions.InvalidTokenException;

public interface TokenValidator {
    void isValidToken(String token) throws InvalidTokenException;
}
