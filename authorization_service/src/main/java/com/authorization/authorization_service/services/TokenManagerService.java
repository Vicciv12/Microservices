package com.authorization.authorization_service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.authorization.authorization_service.core.TokenManager;
import com.authorization.authorization_service.models.Auth;


@Service
public class TokenManagerService implements TokenManager<Auth>{

    @Value("${api.key}")
    private String secret;

    @Override
    public String generateToken(Auth consumer) {
        return createToken(consumer, Algorithm.HMAC256(secret));
    }

    @Override
    public String generateToken(Auth consumer, Algorithm algorithm) {
        return createToken(consumer, algorithm);
    }

    private String createToken(Auth consumer, Algorithm algorithm) {
        String token = null;
        try {
            token = JWT.create()
            .withSubject(consumer.getEmail())
            .sign(algorithm);

            return token;
        } catch (Exception e) {
            return token;
        }
    }
    
}
