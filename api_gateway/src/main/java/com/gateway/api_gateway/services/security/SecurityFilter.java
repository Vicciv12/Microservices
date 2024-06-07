package com.gateway.api_gateway.services.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gateway.api_gateway.core.TokenValidator;
import com.gateway.api_gateway.models.exceptions.InvalidTokenException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Value("${token.header.name}")
    private String tokenName;

    @Autowired
    private TokenValidator tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getToken(request);
            System.out.println(token);
            tokenService.isValidToken(token);
            UsernamePasswordAuthenticationToken anonymousAuthentication = new UsernamePasswordAuthenticationToken(null, null, Arrays.asList());
            SecurityContextHolder.getContext().setAuthentication(anonymousAuthentication);
        } catch (InvalidTokenException e) {
            System.out.println(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request){
        listarHeaders(request);
        return request.getHeader(tokenName);
    }

    private void listarHeaders(HttpServletRequest request) {
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
        String headerName = headerNames.nextElement();
        Enumeration<String> headers = request.getHeaders(headerName);
        while (headers.hasMoreElements()) {
            String headerValue = headers.nextElement();
            System.out.println(headerName + ": " + headerValue);
        }
    }
}
    
}
