package com.scheduling.scheduling_service.models.entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Agendamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_agendamento;

    @Column(nullable = false)
    private String salaCode;

    @Column(nullable = false)
    private LocalDateTime dataInicioAgendamento;

    @Column(nullable = false)
    private LocalDateTime dataFimAgendamento;

    @Column(nullable = false)
    private boolean active;
}
