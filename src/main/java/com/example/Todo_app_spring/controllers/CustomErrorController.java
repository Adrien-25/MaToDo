package com.example.Todo_app_spring.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Retourne le nom de ton fichier HTML d'erreur
        return "error";
    }

    // Optionnel : pour Spring Boot 3.x+
    public String getErrorPath() {
        return "/error";
    }
}
