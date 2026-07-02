package com.hospital.citas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CitasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitasApplication.class, args);
	}
}