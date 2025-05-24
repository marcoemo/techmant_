package com.example.Asignaciones;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.Asignaciones.service.AsignacionService;

@SpringBootApplication
public class AsignacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsignacionesApplication.class, args);
	}
		@Bean
	CommandLineRunner iniciarTecnicos(AsignacionService AR) {
		return args -> AR.cargarTecnicosIniciales();
	}

}
