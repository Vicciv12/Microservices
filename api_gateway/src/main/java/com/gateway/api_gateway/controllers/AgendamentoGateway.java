package com.gateway.api_gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gateway.api_gateway.core.ScheduleGateway;
import com.gateway.api_gateway.models.dto.ScheduleDto;
import com.gateway.api_gateway.models.exceptions.BadGatewayException;
import com.gateway.api_gateway.models.exceptions.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/gateway/schedule")
public class AgendamentoGateway {
    
    @Autowired
    private ScheduleGateway scheduleGateway;
    
    @PostMapping("/agendamento/new")
    private void scheduleRoom(@Valid @RequestBody ScheduleDto scheduleDto, HttpServletRequest request) throws BadGatewayException, BadRequestException{
        scheduleGateway.scheduleRoom(scheduleDto, request );
    }
}
