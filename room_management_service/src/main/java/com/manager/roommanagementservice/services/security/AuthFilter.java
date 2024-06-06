package com.manager.roommanagementservice.services.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.manager.roommanagementservice.core.Extractor;
import com.manager.roommanagementservice.core.TokenValidator;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter{

    @Autowired
    private Extractor<String> extractor;

    @Autowired
    private TokenValidator tokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        validar(request);
        filterChain.doFilter(request, response);
    }

    private void validar(HttpServletRequest request){
        String token = extractor.extractToken(request);
        try {
            tokenValidator.valid(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("", null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            
        }
    }
    
}
