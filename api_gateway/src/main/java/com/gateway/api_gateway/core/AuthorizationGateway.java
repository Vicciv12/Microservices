package com.gateway.api_gateway.core;

import org.springframework.http.ResponseEntity;

import com.gateway.api_gateway.models.exceptions.BadGatewayException;
import com.gateway.api_gateway.models.exceptions.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthorizationGateway {
    ResponseEntity<String> loginAction(String user, String senha, HttpServletRequest request) throws BadGatewayException, BadRequestException;
}
