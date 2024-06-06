package com.authorization.authorization_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authorization.authorization_service.core.ProfessorService;
import com.authorization.authorization_service.core.repositorys.ProfessorRepository;
import com.authorization.authorization_service.models.Auth;
import com.authorization.authorization_service.models.Professor;
import com.authorization.authorization_service.models.dto.NovoProfessorDto;

@Service
public class ProfessorServiceData implements ProfessorService{

    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public void save(NovoProfessorDto novoProfessorDto) throws Exception {
        Professor professor = new Professor();
        Auth auth = new Auth();
        auth.setEmail(novoProfessorDto.getEmail());
        auth.setSenha(novoProfessorDto.getSenha());
        professor.setNome(novoProfessorDto.getNome());
        professor.setSobrenome(novoProfessorDto.getSobrenome());
        professor.setAuth(auth);
        
        try {
            professorRepository.save(professor);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o prefessor");
        }
    }
    
}
