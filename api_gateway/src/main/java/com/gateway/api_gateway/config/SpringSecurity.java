package com.gateway.api_gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gateway.api_gateway.services.security.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private SecurityFilter securityFilter;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/add-user-id-header").permitAll()
                .requestMatchers("/v3/api-docs/swagger-config").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/gateway/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/gateway/cadastro").permitAll()
                .requestMatchers(HttpMethod.GET, "/").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

}
