package com.notification.notification_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.notification.notification_service.core.UserExtractor;

@Service
public class Extractor implements UserExtractor{

    @Value("${api.key}")
    private String secret;

    @Override
    public String getUser(String token) {
        return Valid(token);
    }

    private String Valid(String token) {
        try{
            Algorithm algorithmToken = Algorithm.HMAC256(secret);
            return JWT.require(algorithmToken)
                .build()
            .verify(token)
            .getSubject();

        }catch(JWTVerificationException e){
            return null;
        }
    }
    
}
