package com.example.Todo_app_spring.config;

import com.example.Todo_app_spring.models.User;
import com.example.Todo_app_spring.repositories.UserRepository;
import com.example.Todo_app_spring.config.CustomOAuth2User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        String email = oauthUser.getEmail();

        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            // Optionnel : mise à jour du nom ou d'autres champs
        } else {
            // Création d’un nouveau compte
            user = new User();
            user.setEmail(email);
            user.setUsername(oauthUser.getName()); // Assure-toi que .getName() retourne bien un nom
            user.setProvider(User.Provider.GOOGLE);

            userRepository.save(user);
        }

        // Redirige après succès
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
