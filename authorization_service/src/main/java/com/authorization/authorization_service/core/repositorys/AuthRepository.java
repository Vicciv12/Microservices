package com.authorization.authorization_service.core.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authorization.authorization_service.models.Auth;
import java.util.List;


@Repository
public interface AuthRepository extends JpaRepository<Auth, Long>{
    List<Auth> findByEmail(String email);  
}
