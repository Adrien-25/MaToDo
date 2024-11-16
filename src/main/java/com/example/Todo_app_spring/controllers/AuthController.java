package com.example.Todo_app_spring.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Todo_app_spring.models.User;
import com.example.Todo_app_spring.repositories.UserRepository;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/req/signup")
    public String createUser(@ModelAttribute @Valid User user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            model.addAttribute("errors", errorMessages);
            return "signup";
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Le nom d'utilisateur est déjà utilisé.");
            return "signup";
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "L'adresse email est déjà utilisée.");
            return "signup";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("error", "Les mots de passe ne correspondent pas.");
            return "signup";
        }

        System.out.println("Password: " + user.getPassword());
        System.out.println("ConfirmPassword: " + user.getConfirmPassword());
        System.out.println("user: " + user);
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        // userRepository.save(user);
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            model.addAttribute("success", "Compte créé avec succès. Veuillez vous connecter.");
            return "redirect:/req/login";
        } catch (Exception e) {
            model.addAttribute("error", "Une erreur est survenue lors de la création du compte: " + e.getMessage());
            return "signup";
        }

        // return "redirect:/req/login";
    }

    @GetMapping("/req/login")
    public String showLoginPage() {
        return "login"; // correspond à login.html
    }

    @GetMapping("/req/signup")
    public String showSignUpPage() {
        return "signup"; // correspond à signup.html
    }
}
