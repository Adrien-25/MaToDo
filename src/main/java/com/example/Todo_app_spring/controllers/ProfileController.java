package com.example.Todo_app_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Todo_app_spring.models.User;
import com.example.Todo_app_spring.services.UserService;

@Controller
@RequestMapping("/req/profil")
public class ProfileController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> updateProfile(@RequestParam String field,
            @RequestParam String value,
            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            String username = userDetails.getUsername();
            User user = userService.getUserByUsername(username);

            switch (field) {
                case "username":
                    user.setUsername(value);
                    break;
                case "email":
                    user.setEmail(value);
                    break;
                default:
                    return ResponseEntity.badRequest().body("Champ invalide");
            }

            userService.updateUser(user);
            return ResponseEntity.ok().body("Profil mis à jour avec succès");
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour du profil"+ e);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la mise à jour du profil: " + e.getMessage());
        }
    }
}
