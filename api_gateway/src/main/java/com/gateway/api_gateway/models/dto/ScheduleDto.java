package com.gateway.api_gateway.models.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ScheduleDto {

    @NotEmpty(message = "codigo de sala não pode ser vazio")
    @Size(min = 3, message = "Codigo invalido ex: A000, B0000...")
    private String code;

    @Valid
    @NotNull(message = "informe a data de inicio do agendamento")
    private DataAgendamento dataInicialAgendamento;

    @Valid
    @NotNull(message = "informe a data de termino do agendamento")
    private DataAgendamento dataFinalAgendamento;

    @Data
    @ToString
    public static class DataAgendamento{

        private int dia;

        private int mes;

        private int ano;

        private int hora;

        private int min;
    }
}
