package com.ixxus.example;

import org.apache.camel.util.IOHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ImportResource("classpath*:*-context.xml")
@SpringBootApplication
@ComponentScan(basePackages = { "com.ixxus.example.model", "com.ixxus.example.camel" })
public class CamelMassAggregatorApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CamelMassAggregatorApplication.class, args);

		IOHelper.close(context);
	}
}
