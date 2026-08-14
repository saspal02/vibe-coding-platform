package com.saswat.lovable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LovableApplication {

	public static void main(String[] args) {
		SpringApplication.run(LovableApplication.class, args);
	}

}
