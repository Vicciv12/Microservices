package com.gateway.api_gateway.config;


import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.gateway.api_gateway.controllers.MainController;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

    @Value("${spring.apidoc.url.dev}")
    private String devUrl;

    @Value("${token.header.name}")
    private String tokenName;

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

        SecurityScheme securityScheme = new SecurityScheme()
            .type(SecurityScheme.Type.APIKEY)
            .name(tokenName)
            .in(SecurityScheme.In.HEADER);

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(tokenName);

        return new OpenAPI()
            .info(info)
            .addServersItem(devServer)
            .components(new io.swagger.v3.oas.models.Components().addSecuritySchemes(tokenName, securityScheme))
            .addSecurityItem(securityRequirement);
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("add-user-id-header")
            .addOperationCustomizer((operation, $) -> {
                String[] pathsRequiringAuth = {"com.gateway.api_gateway.controllers.RoomGatewayController"};
                for (String path : pathsRequiringAuth) {
                    if ($.getBeanType().getName().contains(path)){
                        operation.addParametersItem(
                            new HeaderParameter()
                                .name(tokenName)
                                .description("Access Token")
                                .required(true)
                        );
                    }
                }
                return operation;
            })
        .build();
    }
}
