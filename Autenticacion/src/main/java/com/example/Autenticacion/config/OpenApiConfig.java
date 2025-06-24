package com.example.Autenticacion.config;

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
            .title("Equipos de los Clientes y Las categorias")
            .version("1.0")
            .description("API para gestionar los equipos de los clientes y categorias, incluyendo operaciones CRUD y consultas específicas."));
    }

}
