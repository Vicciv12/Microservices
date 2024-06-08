package com.scheduling.scheduling_service.core.repositorys;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scheduling.scheduling_service.models.entitys.Agendamento;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{
    List<Agendamento> findBySalaCodeAndDataInicioAgendamentoLessThanEqualAndDataFimAgendamentoGreaterThanEqual(String salaCode, LocalDateTime dataInicio, LocalDateTime dataFim);
    
    List<Agendamento> findBySalaCode(String salaCode);

}

