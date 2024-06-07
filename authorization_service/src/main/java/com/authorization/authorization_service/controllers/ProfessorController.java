package com.authorization.authorization_service.controllers;

import java.util.HashSet;
import java.util.Set;
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
import com.authorization.authorization_service.models.res.EmailsResponse;

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

    @GetMapping("/all/email")
    public ResponseEntity<EmailsResponse> listAllEmail(){
        Set<String> emailList = new HashSet<>();
        authRepository.findAll().forEach(e -> {
            emailList.add(e.getEmail());
        });
        return ResponseEntity.ok().body(new EmailsResponse(emailList));
    }

}
