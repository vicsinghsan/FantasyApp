package com.fantasy.Fantasy.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.fantasy.Fantasy.app")
public class FantasyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FantasyAppApplication.class, args);
	}

}
