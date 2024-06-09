package com.gateway.api_gateway.models.res;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SalaResponse {
    private Long id;
    private String letra;
    private Integer number;
    private String code;
    private String status; 
}
