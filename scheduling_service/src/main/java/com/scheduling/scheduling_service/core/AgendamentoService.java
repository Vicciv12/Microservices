package com.scheduling.scheduling_service.core;

import org.springframework.http.ResponseEntity;

import com.scheduling.scheduling_service.models.dto.AgendamentoDto;

public interface AgendamentoService {
   ResponseEntity<?> agendarSala(AgendamentoDto agendamento) throws Exception; 
}
