package com.authorization.authorization_service.core.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authorization.authorization_service.models.Auth;
import com.authorization.authorization_service.models.Professor;
import java.util.List;


@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long>{
    List<Professor> findByAuth(Auth auth);
    List<Professor> findByNome(String nome);
    List<Professor> findBySobrenome(String sobrenome);
}
