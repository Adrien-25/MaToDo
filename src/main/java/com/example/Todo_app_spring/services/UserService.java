package com.example.Todo_app_spring.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Todo_app_spring.models.User;
import com.example.Todo_app_spring.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User getCurrentUser() {
        // Obtenez l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Recherchez l'utilisateur dans la base de données
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));
    }

    @Transactional
    public void updateField(String field, String value, Long userId) {
        try {
            switch (field) {
                case "username" ->
                    userRepository.updateUsername(value, userId);
                case "email" ->
                    userRepository.updateEmail(value, userId);
                default ->
                    throw new IllegalArgumentException("Champ invalide");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur de validation des données : " + e.getMessage());
            throw new RuntimeException("Erreur de validation des données", e);
        } catch (Exception e) {
            System.err.println("Erreur imprévue : " + e.getMessage());
            throw new RuntimeException("Erreur imprévue", e);
        }
    }

    public void refreshAuthentication(User user) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
