package com.notification.notification_service.config;


import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.notification.notification_service.Controllers.MainController;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Value("${spring.apidoc.url.dev}")
    private String devUrl;

    static {
        SpringDocUtils.getConfig().addHiddenRestControllers(MainController.class);
    }

    @Bean
    public OpenAPI swaggerDoc(){
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Info info = new Info()
            .title("API GATEWAY")
            .version("1.0")
        .description("api para endpoints da api Gateway.");


        return new OpenAPI()
            .info(info)
        .addServersItem(devServer);
            
    }
}
