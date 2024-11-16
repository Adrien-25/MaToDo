package com.example.Todo_app_spring.controllers;

import java.util.ArrayList;
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

    @GetMapping("/req/login")
    public String showLoginPage() {
        return "login"; // correspond à login.html
    }

    @GetMapping("/req/signup")
    public String showSignUpPage() {
        return "signup"; // correspond à signup.html
    }

    // @PostMapping(value = "/req/signup")
    // public String createUser(@ModelAttribute @Valid User user, BindingResult result, Model model) {
    //     if (result.hasErrors()) {
    //         List<String> errorMessages = result.getAllErrors().stream()
    //                 .map(DefaultMessageSourceResolvable::getDefaultMessage)
    //                 .collect(Collectors.toList());
    //         model.addAttribute("errors", errorMessages);
    //         return "signup";
    //     }
    //     if (userRepository.findByUsername(user.getUsername()).isPresent()) {
    //         model.addAttribute("error", "Le nom d'utilisateur est déjà utilisé.");
    //         return "signup";
    //     }
    //     if (userRepository.findByEmail(user.getEmail()).isPresent()) {
    //         model.addAttribute("error", "L'adresse email est déjà utilisée.");
    //         return "signup";
    //     }
    //     if (!user.getPassword().equals(user.getConfirmPassword())) {
    //         model.addAttribute("error", "Les mots de passe ne correspondent pas.");
    //         return "signup";
    //     }
    //     // user.setPassword(passwordEncoder.encode(user.getPassword()));
    //     // userRepository.save(user);
    //     try {
    //         user.setPassword(passwordEncoder.encode(user.getPassword()));
    //         userRepository.save(user);
    //         model.addAttribute("success", "Compte créé avec succès. Veuillez vous connecter.");
    //         return "redirect:/req/login";
    //     } catch (Exception e) {
    //         model.addAttribute("error", "Une erreur est survenue lors de la création du compte: " + e.getMessage());
    //         return "signup";
    //     }
    @PostMapping(value = "/req/signup")
    public String createUser(@ModelAttribute @Valid User user, BindingResult result, Model model) {

        List<String> errors = new ArrayList<>();

        if (result.hasErrors()) {
            errors.addAll(result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
        }

        errors.addAll(validateUserUniqueness(user));
        errors.addAll(validatePasswordMatch(user));

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "signup";
        }

        // Encodage du mot de passe et sauvegarde de l'utilisateur
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/req/login";
    }

    private List<String> validateUserUniqueness(User user) {
        List<String> errors = new ArrayList<>();
        userRepository.findByUsername(user.getUsername()).ifPresent(u -> errors.add(ERROR_USERNAME_EXISTS));
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> errors.add(ERROR_EMAIL_EXISTS));
        return errors;
    }

    private List<String> validatePasswordMatch(User user) {
        List<String> errors = new ArrayList<>();
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.add(ERROR_PASSWORD_MISMATCH);
        }
        return errors;
    }

// Constantes pour les messages d'erreur
    private static final String ERROR_USERNAME_EXISTS = "Le nom d'utilisateur est déjà utilisé.";
    private static final String ERROR_EMAIL_EXISTS = "L'adresse email est déjà utilisée.";
    private static final String ERROR_PASSWORD_MISMATCH = "Les mots de passe ne correspondent pas.";

}
