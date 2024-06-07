package com.gateway.api_gateway.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.api_gateway.core.RoomGateway;
import com.gateway.api_gateway.core.http.HttpResponse;
import com.gateway.api_gateway.core.http.HttpServices;
import com.gateway.api_gateway.models.dto.EmailsDto;
import com.gateway.api_gateway.models.exceptions.BadGatewayException;
import com.gateway.api_gateway.models.exceptions.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class RoomGatewayService implements RoomGateway{

    
    @Value("${token.header.name}")
    private String tokenName;

    @Autowired
    private HttpServices httpServices;

    @Override
    public ResponseEntity<String> listarSalas(HttpServletRequest request) throws BadGatewayException, BadRequestException {
       String baseUrl = getDnsUrl("room_management_service");
        HttpResponse response = null;
        try {
            Map<String, String> headers = new HashMap<>(){{
                put(tokenName, extractToken(request));
                put("Content-Type", "application/json");
            }};
            response = httpServices.sendGet(baseUrl+"/room/all", headers);

        } catch (Exception e) {
            throw new BadGatewayException("BadGateway", 502);
        }
           
        if(response.getStatusCode() != 200){
            throw new BadRequestException(response.getBody(), response.getStatusCode());
        }

        return ResponseEntity.ok(response.getBody());
    }

    @Override
    public ResponseEntity<?> newRoom(String bloco, int num, HttpServletRequest request) throws BadGatewayException, BadRequestException {


        String baseUrl = getDnsUrl("room_management_service");
        HttpResponse response = null;
        try {
            String JsonBulder = "{\"bloco\":\"%s\", \"num\":\"%d\"}";
            String jsonBody = String.format(JsonBulder, bloco, num);
            Map<String, String> headers = new HashMap<>(){{
                put(tokenName, extractToken(request));
                put("Content-Type", "application/json");
            }};
            response = httpServices.sendPost(baseUrl+"/room/new", jsonBody, headers);

        } catch (Exception e) {
            throw new BadGatewayException("BadGateway", 502);
        }
           
        if(response.getStatusCode() != 200){
            throw new BadRequestException(response.getBody(), response.getStatusCode());
        }

        notificateNewAsync(request);

        return ResponseEntity.ok(response.getBody());
    }

    @Override
    public ResponseEntity<?> updateRoom(String bloco, int num, String status, String salaCode, HttpServletRequest request) throws BadGatewayException, BadRequestException {
        String baseUrl = getDnsUrl("room_management_service");
        HttpResponse response = null;
        try {
            String JsonBulder = "{\"bloco\":\"%s\", \"num\":\"%d\", \"status\": \"%s\"}";
            String jsonBody = String.format(JsonBulder, bloco, num, status);
            Map<String, String> headers = new HashMap<>(){{
                put(tokenName, extractToken(request));
                put("Content-Type", "application/json");
            }};
            response = httpServices.sendPut(baseUrl+"/room/update/"+salaCode, jsonBody, headers);

        } catch (Exception e) {
            throw new BadGatewayException("BadGateway", 502);
        }
           
        if(response.getStatusCode() != 200){
            throw new BadRequestException(response.getBody(), response.getStatusCode());
        }

        return ResponseEntity.ok(response.getBody());
    }
    
    @Override
    public ResponseEntity<?> updateStatusRoom(String salaCode, HttpServletRequest request) throws BadGatewayException, BadRequestException {
        String baseUrl = getDnsUrl("room_management_service");
        HttpResponse response = null;
        try {
            Map<String, String> headers = new HashMap<>(){{
                put(tokenName, extractToken(request));
                put("Content-Type", "application/json");
            }};
            response = httpServices.sendPut(baseUrl+"/room/status/"+salaCode, "", headers);

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
        if(appName.equals("authorization_service")){
            return "http://localhost:8081";
        }
        return "http://localhost:8082";
    }
    
    @SuppressWarnings({"unused"})
    private void notificateNewAsync(HttpServletRequest request){
        Thread notificator = new Thread(() -> {
            try {
                Set<String> emails = getListEmails(request);
            } catch (BadGatewayException |  BadRequestException e) { 
                //add log
            }
            
        });
        notificator.start();
    }

    private Set<String> getListEmails(HttpServletRequest request) throws BadGatewayException, BadRequestException{
        HttpResponse response = null;
        Set<String> emailsList = new HashSet<>();
        String baseUrl = getDnsUrl("authorization_service");
        try {
            Map<String, String> headers = new HashMap<>(){{
                put(tokenName, extractToken(request));
                put("Content-Type", "application/json");
            }};
            response = httpServices.sendGet(baseUrl+"/all/email", headers);
        } catch (Exception e) {
            throw new BadGatewayException("BadGateway", 502);
        }



        if (response != null && response.getStatusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                EmailsDto emails = mapper.readValue(response.getBody(), EmailsDto.class);
                emailsList = emails.getEmails();
            } catch (Exception e) {
                throw new BadRequestException("BadRequest: "+e.getMessage(), 400);
            }
            
        } else {
            throw new BadRequestException("BadRequest", 400);
        }

        return emailsList;

    }
    

}
