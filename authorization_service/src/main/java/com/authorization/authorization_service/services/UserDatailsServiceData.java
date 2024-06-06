package com.authorization.authorization_service.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.authorization.authorization_service.core.repositorys.AuthRepository;
import com.authorization.authorization_service.models.Auth;

@Service
public class UserDatailsServiceData implements UserDetailsService{

    @Autowired
    private AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Auth> listOfAuths = authRepository.findByEmail(username);
        if(listOfAuths.size() <= 0){
            throw new UsernameNotFoundException("usuario não encontrado");
        }

        return listOfAuths.get(0);
    }
    
}
