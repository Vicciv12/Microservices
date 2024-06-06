package com.manager.roommanagementservice.models.res;

import java.util.List;

import com.manager.roommanagementservice.models.Sala;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SalasResponse {
    private List<Sala> salas;
}
