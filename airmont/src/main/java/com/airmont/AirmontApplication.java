package com.airmont;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AirmontApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirmontApplication.class, args);
	}
}