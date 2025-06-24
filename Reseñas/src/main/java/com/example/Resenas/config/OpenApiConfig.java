package com.example.Resenas.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
        .info(new Info()
            .title("Reseñas")
            .version("1.0")
            .description("API para gestionar las reseñas, incluyendo operaciones CRUD y consultas específicas."));
    }

}