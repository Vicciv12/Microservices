package com.authorization.authorization_service.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NovoProfessorDto {
    
    @NotBlank(message = "O nome do prefessor não pode ser vazio")
    private String nome;

    @NotBlank(message = "O sobrenome do prefessor não pode ser vazio")
    private String sobrenome;

    @NotBlank(message = "O email do prefessor não pode ser vazio")
    @Email(message = "Email invalido")
    private String email;

    @NotBlank(message = "A senha do prefessor não pode ser vazio")
    private String senha;
}
