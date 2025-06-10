package com.aura8.general_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GeneralBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeneralBackendApplication.class, args);
	}

}
