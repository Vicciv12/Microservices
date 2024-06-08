package com.notification.notification_service.models.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MutipleNotificationDto {
    
    @NotNull(message = "a lista de emails de recebimento não pode ser vazia")
    private Set<@NotBlank(message = "email de recebimento não pode ser vazio") String> receivers;

    @NotBlank(message = "o título não pode ser nulo")
    @Size(max = 100, message = "o título não pode ter mais de 100 caracteres")
    private String title;

    @NotBlank(message = "a mensagem não pode ser nula")
    @Size(max = 1000, message = "a mensagem não pode ter mais de 1000 caracteres")
    private String message;

}
