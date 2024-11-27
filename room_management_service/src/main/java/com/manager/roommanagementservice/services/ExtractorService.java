package com.manager.roommanagementservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.manager.roommanagementservice.core.Extractor;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ExtractorService implements Extractor<String>{

    @Value("${token.header.name}" )
    private String tokenHeaderName;

    @Value("${api.key}")
    private String secret;

    @Override
    public String extractToken(HttpServletRequest request) {
        StringBuilder tokenBulBuilder = new StringBuilder();
        tokenBulBuilder.append(request.getHeader(tokenHeaderName));
        return tokenBulBuilder.toString();
    }

    @Override
    public String extractDataByToken(HttpServletRequest request) {
        String token = extractToken(request);
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }

    @Override
    public String extractDataByToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }
    
}
