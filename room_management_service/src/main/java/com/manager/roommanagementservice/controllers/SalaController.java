package com.manager.roommanagementservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.manager.roommanagementservice.core.RequestValidator;
import com.manager.roommanagementservice.core.RoomService;
import com.manager.roommanagementservice.models.dto.NovaSalaDto;
import com.manager.roommanagementservice.models.dto.UpdateSalaDto;
import com.manager.roommanagementservice.models.res.SalasResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/room")
public class SalaController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RequestValidator requestValidator;
    
    @GetMapping("/all")
    public ResponseEntity<SalasResponse> listarAllRooms() throws Exception{
        return ResponseEntity.ok().body(new SalasResponse(roomService.listAll()));
    }

    @PostMapping("/new")
    public void newRoom(@Valid @RequestBody NovaSalaDto salaDto, BindingResult bindingResult) throws Exception{
        requestValidator.isValidData(bindingResult);
        roomService.save(salaDto);
    }

    @DeleteMapping("/delete/{code}")
    public void deleteRoom(@PathVariable(name = "code") String code) throws Exception{
        roomService.deleteRoom(code);
    }

    @PutMapping("/update/{code}")
    public void UpdateRoom(@Valid @RequestBody UpdateSalaDto salaDto, @PathVariable(name = "code") String code ,BindingResult bindingResult) throws Exception{
        requestValidator.isValidData(bindingResult);
        roomService.save(salaDto, code);
    }

    @PutMapping("/status/{code}")
    public void UpdateRoomStatus(@PathVariable(name = "code") String code) throws Exception{
        roomService.changeStatus(code);
    }
}
