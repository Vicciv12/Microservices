package com.gateway.api_gateway.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Value("${spring.apidoc.url.dev}")
    private String devUrl;
    
    public OpenAPI swaggerDoc(){
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Info info = new Info()
            .title("API GATEWAY")
            .version("1.0")
        .description("api para endpoints da api Gateway.");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }

}
