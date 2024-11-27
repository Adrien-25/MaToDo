package com.example.Todo_app_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class WebConfig {

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();  // Permet de traiter les méthodes HTTP "non standard" (DELETE, PUT) envoyées en POST avec un champ "_method"
    }
}
