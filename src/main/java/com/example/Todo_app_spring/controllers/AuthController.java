package com.example.Todo_app_spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.Todo_app_spring.models.User;
import com.example.Todo_app_spring.repositories.UserRepository;

@Controller
public class AuthController {

  @Autowired
    private UserRepository UserRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping(value = "/req/signup", consumes = "application/json")
    public User createUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserRepository.save(user);
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
