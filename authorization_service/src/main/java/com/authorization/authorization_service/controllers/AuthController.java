package com.authorization.authorization_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.authorization_service.core.AuthService;
import com.authorization.authorization_service.core.RequestValidator;
import com.authorization.authorization_service.models.dto.AuthDto;

import jakarta.validation.Valid;

@RestController
public class AuthController {
    
    @Autowired
    private RequestValidator requestValidator;

    @Autowired
    private AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<String> newProfessor(@Valid @RequestBody AuthDto authDto, BindingResult bindingResult) throws Exception{
        requestValidator.isValidData(bindingResult);
        String token = authService.authOk(authDto.getEmail(), authDto.getSenha());
        return ResponseEntity.ok().body(String.format("{\"token\":\"%s\"}", token));
    }

}
