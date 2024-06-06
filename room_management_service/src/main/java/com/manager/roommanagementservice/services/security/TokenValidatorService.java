package com.manager.roommanagementservice.services.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.manager.roommanagementservice.core.TokenValidator;

@Service
public class TokenValidatorService implements TokenValidator {

    @Value("${api.key}")
    private String secret;

    @Override
    public void valid(String token) throws Exception {
        validToken(token);
    }

    private void validToken(String token) throws Exception {
        Algorithm algorithmToken = Algorithm.HMAC256(secret);
        JWT.require(algorithmToken)
                .build()
                .verify(token);
    }

}
