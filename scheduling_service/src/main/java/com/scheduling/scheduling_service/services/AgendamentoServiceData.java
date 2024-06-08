package com.scheduling.scheduling_service.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.scheduling.scheduling_service.core.AgendamentoService;
import com.scheduling.scheduling_service.core.repositorys.AgendamentoRepository;
import com.scheduling.scheduling_service.models.dto.AgendamentoDto;
import com.scheduling.scheduling_service.models.dto.AgendamentoDto.DataAgendamento;
import com.scheduling.scheduling_service.models.entitys.Agendamento;

@Service
public class AgendamentoServiceData implements AgendamentoService{

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Override
    public ResponseEntity<?> agendarSala(AgendamentoDto agendamento) throws Exception{
        LocalDateTime initTime = formatDate(agendamento.getDataInicialAgendamento());
        LocalDateTime finalTime = formatDate(agendamento.getDataFinalAgendamento());
        validateDates(initTime,finalTime);
        boolean existeConflito = false;
        List<Agendamento> agendamentosThen = agendamentoRepository.findBySalaCode(agendamento.getCode());

        for (Agendamento agendamentoExistente : agendamentosThen) {
            System.out.println(agendamentoExistente.getSalaCode());
            if (agendamentoExistente.isActive()) {
                LocalDateTime existingStartTime = agendamentoExistente.getDataInicioAgendamento();
                LocalDateTime existingEndTime = agendamentoExistente.getDataFimAgendamento();
                
                if ((existingStartTime.isBefore(finalTime) || existingStartTime.equals(finalTime)) &&
                    (existingEndTime.isAfter(initTime) || existingEndTime.equals(initTime))) {
                    existeConflito = true;
                    break;
                }
            }
        }

        if(existeConflito){
            throw new Exception("Sala ja agendada");
        }

        Agendamento agenda = new Agendamento();
        agenda.setActive(true);
        agenda.setDataFimAgendamento(finalTime);
        agenda.setDataInicioAgendamento(finalTime);
        agenda.setSalaCode(agendamento.getCode());

        final Agendamento agendaChange = agendamentoRepository.save(agenda);
        Duration duration = Duration.between(initTime, finalTime);
        long differenceInMilliseconds = duration.toMillis();

        Thread change = new Thread(() -> {
            try {
                Thread.sleep(differenceInMilliseconds);
                agendaChange.setActive(false);
                agendamentoRepository.save(agendaChange);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        change.start();
        return ResponseEntity.ok().body("{\"run\":"+differenceInMilliseconds+"}");
    }


    private LocalDateTime formatDate(DataAgendamento dataAgendamentoBase){
        return LocalDateTime.of(
            dataAgendamentoBase.getAno(),
            dataAgendamentoBase.getMes(),
            dataAgendamentoBase.getDia(),
            dataAgendamentoBase.getHora(),
            dataAgendamentoBase.getMin()
        );
    }
    
    private void validateDates(LocalDateTime initTime, LocalDateTime finalTime) throws Exception{
        LocalDateTime now = LocalDateTime.now();

        if (initTime.isBefore(now)) {
            throw new Exception("A data inicial não pode ser mais cedo que a data atual.");
        }

        if (!finalTime.isAfter(initTime)) {
            throw new Exception("A data final não pode ser mais cedo ou igual que a data inicial.");
        }
    }

}
