package com.br.projetoMilitar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ProjetoMilitarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoMilitarApplication.class, args);
	}
}