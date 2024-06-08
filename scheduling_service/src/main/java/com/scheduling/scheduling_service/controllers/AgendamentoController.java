package com.scheduling.scheduling_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.scheduling.scheduling_service.core.AgendamentoService;
import com.scheduling.scheduling_service.models.dto.AgendamentoDto;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/agendamento")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;
    
    @PostMapping("/new")
    public ResponseEntity<?> agendarSala(@Valid @RequestBody AgendamentoDto dto) throws Exception{
        return agendamentoService.agendarSala(dto);
    }

}
