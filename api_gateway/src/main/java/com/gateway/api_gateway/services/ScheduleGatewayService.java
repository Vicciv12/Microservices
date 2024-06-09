package com.gateway.api_gateway.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.api_gateway.core.ScheduleGateway;
import com.gateway.api_gateway.core.http.HttpResponse;
import com.gateway.api_gateway.core.http.HttpServices;
import com.gateway.api_gateway.models.dto.ScheduleDto;
import com.gateway.api_gateway.models.dto.ScheduleDto.DataAgendamento;
import com.gateway.api_gateway.models.exceptions.BadGatewayException;
import com.gateway.api_gateway.models.exceptions.BadRequestException;
import com.gateway.api_gateway.models.res.RunAgendamentoDto;
import com.gateway.api_gateway.models.res.SalaResponse;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ScheduleGatewayService implements ScheduleGateway{

    @Value("${token.header.name}")
    private String tokenName;

    @Autowired
    private HttpServices httpServices;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public void scheduleRoom(ScheduleDto scheduleDto, HttpServletRequest request) throws BadGatewayException, BadRequestException {
        SalaResponse sala = obterDisponibilidade(scheduleDto.getCode(), request);
        long runtime = agendar(scheduleDto, request);
        changeSalaStatus(sala.getCode(), request);
        notificate(scheduleDto, request);
        changeSalaStatusAsync(tokenName, request, runtime);
    }


    private SalaResponse obterDisponibilidade(String salaCode, HttpServletRequest request) throws BadGatewayException, BadRequestException {
        String baseUrl = getDnsUrl("room_management_service");
        HttpResponse response = null;
        try {
            Map<String, String> headers = new HashMap<>(){{
                put(tokenName, extractToken(request));
                put("Content-Type", "application/json");
            }};
            response = httpServices.sendGet(baseUrl+"/room/find/"+salaCode, headers);

        } catch (Exception e) {
            throw new BadGatewayException("BadGateway", 502);
        }
           
        if(response.getStatusCode() != 200){
            throw new BadRequestException(response.getBody(), response.getStatusCode());
        }

        SalaResponse sala = verificarDisponibilidade(response.getBody());
        if(!sala.getStatus().equalsIgnoreCase("DISPONIVEL")){
            throw new BadGatewayException("sala não disponivel", 400);
        }

        return sala;
    }

    private String getDnsUrl(String appName){
        List<ServiceInstance> instances = discoveryClient.getInstances(appName);
        if (instances != null && !instances.isEmpty()) {
            return instances.get(0).getUri().toString();
        }
        return "http://localhost:8083";
    }
    
    private String extractToken(HttpServletRequest request){
        String token = request.getHeader(tokenName);

        if(token == null){
            return "";
        }

        return token;
    }

    private SalaResponse verificarDisponibilidade(String body) throws BadRequestException{
        ObjectMapper mapper = new ObjectMapper();
        SalaResponse response = null;
        try {
            response = mapper.readValue(body, SalaResponse.class);
            
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(), 400);
        }

        return response;
    }

    private long agendar(ScheduleDto scheduleDto, HttpServletRequest request) throws BadGatewayException, BadRequestException {
        String baseUrl = getDnsUrl("scheduling_service");
        HttpResponse response = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonBody = mapper.writeValueAsString(scheduleDto);
            Map<String, String> headers = new HashMap<>(){{
                put(tokenName, extractToken(request));
                put("Content-Type", "application/json");
            }};
            response = httpServices.sendPost(baseUrl+"/agendamento/new", jsonBody, headers);

        } catch (Exception e) {
            throw new BadGatewayException("BadGateway", 502);
        }
           
        if(response.getStatusCode() != 200){
            throw new BadRequestException(response.getBody(), response.getStatusCode());
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            RunAgendamentoDto runTime = mapper.readValue(response.getBody(), RunAgendamentoDto.class);
            return runTime.getRun();  
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage(), 400);
        }
    }

    private void changeSalaStatus(String salaCode, HttpServletRequest request) throws BadGatewayException, BadRequestException{
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

    }

    private void changeSalaStatusAsync(String salaCode, HttpServletRequest request, long perTime){
        Map<String, String> headers = new HashMap<>(){{
            put(tokenName, extractToken(request));
            put("Content-Type", "application/json");
        }};
        Thread execute = new Thread(() -> {
            String baseUrl = getDnsUrl("room_management_service");
            try {
                Thread.sleep(perTime);
                httpServices.sendPut(baseUrl+"/room/status/"+salaCode, "", headers);
    
            } catch (Exception e) {
                //add log
            }
        });
        execute.start();
    }

    private void notificate(ScheduleDto scheduleDto, HttpServletRequest request) throws BadGatewayException, BadRequestException{
        String url = getDnsUrl("notification_service")+"/me/notificate";
        Map<String, String> headers = new HashMap<>(){{
            put(tokenName, extractToken(request));
            put("Content-Type", "application/json");
        }};
        Thread notificator = new Thread(() -> {
            HttpResponse response = null;
            try {
                DataAgendamento inicio = scheduleDto.getDataInicialAgendamento();
                String msg = "Sala " + scheduleDto.getCode() +" Agendada para " + inicio.getDia()+"/"+inicio.getMes() + "as " + inicio.getHora()+":"+inicio.getMin();
                String jsonBulder = "{\"title\":\"%s\", \"message\":\"%s\"}";
                String json = String.format(jsonBulder, "Aviso Sala Agendada", msg);
                response = httpServices.sendPost(url, json, headers);

                if(response.getStatusCode() != 200){
                    throw new BadRequestException(response.getBody(), response.getStatusCode());
                }
            } catch (BadGatewayException |  BadRequestException e) { 
                //add log
                System.out.println(e.getMessage());
            }catch(Exception e){
                System.out.println(e.getMessage());
                //add log
            }
        });
        notificator.start();
    }
}
