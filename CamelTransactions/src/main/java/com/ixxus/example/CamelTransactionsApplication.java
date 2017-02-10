package com.ixxus.example;

import org.apache.camel.util.IOHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = { "com.ixxus.example.domain.repository" })
@EntityScan(basePackages = { "com.ixxus.example.domain.model" })
@ComponentScan(basePackages = { "com.ixxus.example.camel" })
public class CamelTransactionsApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(CamelTransactionsApplication.class, args);

        IOHelper.close(context);
    }
}
