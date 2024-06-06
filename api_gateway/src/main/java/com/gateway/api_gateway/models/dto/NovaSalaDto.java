package com.gateway.api_gateway.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NovaSalaDto {

    @NotBlank(message = "O bloco da sala não pode ser vazio")
    @Size(max = 1, message = "O bloco é apenas uma letra")
    private String bloco;

    @Positive(message = "o numero da sala tem que ser positivo")
    private int num;
}
