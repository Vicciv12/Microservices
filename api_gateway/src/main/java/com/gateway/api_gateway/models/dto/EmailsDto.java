package com.gateway.api_gateway.models.dto;

import java.util.Set;

import lombok.Data;

@Data
public class EmailsDto {
    private Set<String> emails;
}
