package com.gateway.api_gateway.core;

import org.springframework.http.ResponseEntity;

import com.gateway.api_gateway.models.exceptions.BadGatewayException;
import com.gateway.api_gateway.models.exceptions.BadRequestException;

import jakarta.servlet.http.HttpServletRequest;

public interface RoomGateway {
    ResponseEntity<?> listarSalas(HttpServletRequest request) throws BadGatewayException, BadRequestException;
    ResponseEntity<?> newRoom(String bloco, int num, HttpServletRequest request) throws BadGatewayException, BadRequestException;
    ResponseEntity<?> updateRoom(String bloco, int num, String status, String salaCode, HttpServletRequest request) throws BadGatewayException, BadRequestException;
    ResponseEntity<?> updateStatusRoom(String salaCode, HttpServletRequest request) throws BadGatewayException, BadRequestException;
}
