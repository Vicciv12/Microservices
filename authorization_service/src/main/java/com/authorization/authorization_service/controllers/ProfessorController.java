package com.authorization.authorization_service.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.authorization.authorization_service.core.ProfessorService;
import com.authorization.authorization_service.core.RequestValidator;
import com.authorization.authorization_service.core.repositorys.AuthRepository;
import com.authorization.authorization_service.models.dto.NovoProfessorDto;
import jakarta.validation.Valid;

@RestController
public class ProfessorController {

    @Autowired
    private RequestValidator requestValidator;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AuthRepository authRepository;
   
    @PostMapping("/new/professor")
    public void newProfessor(@Valid @RequestBody NovoProfessorDto dto, BindingResult bindingResult) throws Exception{
        requestValidator.isValidData(bindingResult);
        professorService.save(dto);
    }

    @GetMapping("all/email")
    public ResponseEntity<List<String>> listAllEmail(){
        List<String> emailList = authRepository.findAll()
            .stream()
            .map(e -> e.getEmail())
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(emailList);
    }

}
