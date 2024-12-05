package com.example.Todo_app_spring.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Todo_app_spring.models.User;
import com.example.Todo_app_spring.repositories.UserRepository;
import com.example.Todo_app_spring.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/req/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/req/signup")
    public String showSignUpPage() {
        return "signup";
    }

    @PostMapping("/user/change-password")
    public ResponseEntity<String> changePassword(HttpServletRequest request,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword) {
        try {
            String username = request.getUserPrincipal().getName();
            User user = userService.getUserByUsername(username);

            // Vérifier que l'ancien mot de passe est correct
            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                return ResponseEntity.ok("Ancien mot de passe incorrect.");
            }

            if (!newPassword.equals(confirmPassword)) {
                return ResponseEntity.ok("Les nouveaux mots de passe ne correspondent pas.");
            }
           
            // Mettre à jour le mot de passe
            userService.updatePassword(user.getId(), passwordEncoder.encode(newPassword));
            return ResponseEntity.ok("Mot de passe modifié avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la modification du mot de passe : " + e.getMessage());
        }
    }

    @PostMapping("/user/delete")
    public String deleteUser(HttpServletRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            userService.deleteUser(user.getId());
            SecurityContextHolder.getContext().setAuthentication(null);
            return "redirect:/login";
        } catch (Exception e) {
            return "redirect:/error";

        }
    }

    @PostMapping(value = "/req/profil")
    public ResponseEntity<?> updateUser(@RequestParam String field,
            @RequestParam String value) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);

            userService.updateField(field, value);

            if ("username".equals(field)) {
                user.setUsername(value); // Met à jour le champ localement
                userService.refreshAuthentication(user); // Rafraîchit le contexte
            }
            return ResponseEntity.ok().body("Profil mis à jour avec succès");
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour du profil" + e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la mise à jour du profil: " + e.getMessage());
        }
    }

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
        if (userRepository.existsByUsername(user.getUsername())) {
            errors.add(ERROR_USERNAME_EXISTS);
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            errors.add(ERROR_EMAIL_EXISTS);
        }

        // userRepository.findByUsername(user.getUsername()).ifPresent((u) -> errors.add(ERROR_USERNAME_EXISTS));
        // userRepository.findByEmail(user.getEmail()).ifPresent(u -> errors.add(ERROR_EMAIL_EXISTS));
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
