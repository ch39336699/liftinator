package com.edge.configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Project: liftinator
 * File: SwaggerConfig.java
 * Author: Chris Harper
 * The SwaggerConfig class configures Swagger for API documentation in a Spring Boot application.
 * It uses the OpenAPI specification to define metadata about the API such as the title, description, and version.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Your API Title")
                        .description("Your API Description")
                        .version("1.0.0"));
    }
}