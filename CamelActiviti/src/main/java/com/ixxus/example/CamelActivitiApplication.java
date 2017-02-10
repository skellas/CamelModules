package com.ixxus.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.ixxus.example.camel" })
public class CamelActivitiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CamelActivitiApplication.class, args);
	}
}
