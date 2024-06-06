package com.authorization.authorization_service.core;

import com.authorization.authorization_service.models.dto.NovoProfessorDto;

public interface ProfessorService {
    void save(NovoProfessorDto novoProfessorDto) throws Exception;
}
