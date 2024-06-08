package com.gateway.api_gateway.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gateway.api_gateway.core.RoomGateway;
import com.gateway.api_gateway.models.dto.NovaSalaDto;
import com.gateway.api_gateway.models.dto.UpdateSalaDto;
import com.gateway.api_gateway.models.exceptions.BadGatewayException;
import com.gateway.api_gateway.models.exceptions.BadRequestException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/gateway")
@SecurityRequirement(name = "authorization")
public class RoomGatewayController {

    @Autowired
    private RoomGateway roomGateway;
    
    @GetMapping("/room/all")
    @Operation(summary = "List all rooms", security = @SecurityRequirement(name = "Authorization"))
    
    public ResponseEntity<?> listAllRooms(HttpServletRequest request) throws BadGatewayException, BadRequestException {
        return roomGateway.listarSalas(request);
    }

    @PostMapping("/room/new")
    public ResponseEntity<?> newRoom(@RequestBody NovaSalaDto dto, HttpServletRequest request) throws BadGatewayException, BadRequestException {
        return roomGateway.newRoom(dto.getBloco(), dto.getNum(), request);
    }

    @PutMapping("/room/update/{code}")
    public ResponseEntity<?> updateRoom(@RequestBody UpdateSalaDto dto, @PathVariable(name = "code") String code, HttpServletRequest request)  throws BadGatewayException, BadRequestException{
        return roomGateway.updateRoom(dto.getBloco(), dto.getNum(), dto.getStatus(), code, request);
    }

    @DeleteMapping("/room/delete/{code}")
    public ResponseEntity<?> updateRoom(@PathVariable(name = "code") String code, HttpServletRequest request)  throws BadGatewayException, BadRequestException{
        return roomGateway.deleteRoom(code, request);
    }

    @PutMapping("/room/status/{code}")
    public ResponseEntity<?> updateStatusRoom(@PathVariable(name = "code") String code, HttpServletRequest request)  throws BadGatewayException, BadRequestException{
        return roomGateway.updateStatusRoom(code, request);
    }
}
