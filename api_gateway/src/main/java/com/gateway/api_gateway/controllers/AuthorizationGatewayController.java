package com.gateway.api_gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gateway.api_gateway.core.AuthorizationGateway;
import com.gateway.api_gateway.models.dto.AuthDto;
import com.gateway.api_gateway.models.exceptions.BadGatewayException;
import com.gateway.api_gateway.models.exceptions.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/gateway")
public class AuthorizationGatewayController {

    @Autowired
    private AuthorizationGateway authorizationGateway;
    
    @PostMapping("/login")
    public ResponseEntity<String> loginGateway(@Valid @RequestBody AuthDto dto, HttpServletRequest request) throws BadGatewayException, BadRequestException{
        return authorizationGateway.loginAction(dto.getEmail(), dto.getSenha(), request);
    }

}
