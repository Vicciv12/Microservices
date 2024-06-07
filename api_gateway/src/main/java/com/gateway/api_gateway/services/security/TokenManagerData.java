package com.gateway.api_gateway.services.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gateway.api_gateway.core.TokenValidator;
import com.gateway.api_gateway.models.exceptions.InvalidTokenException;

@Service
public class TokenManagerData implements TokenValidator{

    @Value("${api.key}")
    private String key;

    @Override
    public void isValidToken(String token) throws InvalidTokenException {
        Valid(token, Algorithm.HMAC256(key));
    }


    private void Valid(String token, Algorithm algorithm) throws InvalidTokenException{

        if(token == null){
            throw new InvalidTokenException("token invalido");
        }else if(token.isEmpty()){
            throw new InvalidTokenException("token invalido");
        }

        try{
            Algorithm algorithmToken = algorithm;
            JWT.require(algorithmToken)
                .build()
            .verify(token);

        }catch(JWTVerificationException e){
            throw new InvalidTokenException("token invalido");
        }
    }
    
}
