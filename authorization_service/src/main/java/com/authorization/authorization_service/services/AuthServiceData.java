package com.authorization.authorization_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.authorization.authorization_service.core.AuthService;
import com.authorization.authorization_service.core.TokenManager;
import com.authorization.authorization_service.models.Auth;

@Service
public class AuthServiceData implements AuthService{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager<Auth> tokenManager;

    @Override
    public String authOk(String user, String password) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(user, password);
        Authentication authenticator = authenticationManager.authenticate(usernamePassword);
        if(authenticator.getPrincipal() instanceof Auth){
           Auth auth =  (Auth)authenticator.getPrincipal();
           String token = tokenManager.generateToken(auth);
           stringBuilder.append(token);
        }
        
        return stringBuilder.toString();
    }
    
}
