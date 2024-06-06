package com.gateway.api_gateway.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.gateway.api_gateway.core.AuthorizationGateway;
import com.gateway.api_gateway.core.http.HttpResponse;
import com.gateway.api_gateway.core.http.HttpServicesPost;
import com.gateway.api_gateway.models.exceptions.BadGatewayException;
import com.gateway.api_gateway.models.exceptions.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthorizationGatewayServiceData implements AuthorizationGateway{

    @Value("${token.header.name}")
    private String tokenName;

    @Autowired
    private HttpServicesPost httpServicesPost;

    @Override
    public ResponseEntity<String> loginAction(String user, String senha, HttpServletRequest request) throws BadGatewayException, BadRequestException{
        String baseUrl = getDnsUrl("authorization_service");
        HttpResponse response = null;
        try {
            String JsonBulder = "{\"email\":\"%s\", \"senha\":\"%s\"}";
            String jsonBody = String.format(JsonBulder, user, senha);
            Map<String, String> headers = new HashMap<>(){{
                put(tokenName, extractToken(request));
                put("Content-Type", "application/json");
            }};
            response = httpServicesPost.sendPost(baseUrl+"/auth", jsonBody, headers);

        } catch (Exception e) {
            throw new BadGatewayException("BadGateway", 502);
        }
           
        if(response.getStatusCode() != 200){
            throw new BadRequestException(response.getBody(), response.getStatusCode());
        }

        return ResponseEntity.ok(response.getBody());
    }
    

    private String extractToken(HttpServletRequest request){
        String token = request.getHeader(tokenName);

        if(token == null){
            return "";
        }

        return token;
    }

    private String getDnsUrl(String appName){
        return "http://localhost:8081";
    }
}
