package com.example.Todo_app_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityUtils {

    // private final PasswordEncoder passwordEncoder;

    // public SecurityUtils() {
    //     this.passwordEncoder = new BCryptPasswordEncoder();
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Ajoutez ici d'autres méthodes utilitaires liées à la sécurité si nécessaire
}
