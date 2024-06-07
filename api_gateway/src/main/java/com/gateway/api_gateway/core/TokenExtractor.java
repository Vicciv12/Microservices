package com.gateway.api_gateway.core;

import com.gateway.api_gateway.models.exceptions.InvalidTokenException;

public interface TokenExtractor {
    String extractEmail(String token) throws InvalidTokenException;
}
