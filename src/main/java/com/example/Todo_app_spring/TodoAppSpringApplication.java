package com.example.Todo_app_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class TodoAppSpringApplication {

    @Autowired
private Environment env;

@PostConstruct
public void checkEnvironmentVariables() {
    System.out.println("DB_URL: " + env.getProperty("DB_URL"));
    System.out.println("GOOGLE_CLIENT_ID: " + env.getProperty("GOOGLE_CLIENT_ID"));
    System.out.println("GOOGLE_CLIENT_SECRET: " + env.getProperty("GOOGLE_CLIENT_SECRET"));
}
    public static void main(String[] args) {
        SpringApplication.run(TodoAppSpringApplication.class, args);
    }

}
