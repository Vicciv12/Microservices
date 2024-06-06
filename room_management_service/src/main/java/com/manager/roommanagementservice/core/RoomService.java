package com.manager.roommanagementservice.core;

import java.util.List;

import com.manager.roommanagementservice.models.Sala;
import com.manager.roommanagementservice.models.dto.NovaSalaDto;
import com.manager.roommanagementservice.models.dto.UpdateSalaDto;

public interface RoomService {
    void save(NovaSalaDto salaDto) throws Exception;   
    void save(UpdateSalaDto salaDto, String code) throws Exception;   
    List<Sala> listAll();
    void changeStatus(String code) throws Exception;
}
