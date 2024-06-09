package com.gateway.api_gateway.core;

import com.gateway.api_gateway.models.dto.ScheduleDto;
import com.gateway.api_gateway.models.exceptions.BadGatewayException;
import com.gateway.api_gateway.models.exceptions.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;

public interface ScheduleGateway {
    void scheduleRoom(ScheduleDto scheduleDto, HttpServletRequest request) throws BadGatewayException, BadRequestException;
}
