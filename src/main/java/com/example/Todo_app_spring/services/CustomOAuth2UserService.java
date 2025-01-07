package com.example.Todo_app_spring.services;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.Todo_app_spring.config.CustomOAuth2User;
import com.example.Todo_app_spring.models.User;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;

    // @Autowired
    public CustomOAuth2UserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String email = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");

        User user = userService.processOAuthPostLogin(email, name);

        return new CustomOAuth2User(oauth2User, user.getId());
    }

    // private final UserRepository userRepository;
    // private final SecurityUtils securityUtils;
    // public CustomOAuth2UserService(UserRepository userRepository, SecurityUtils  securityUtils) {
    //     this.userRepository = userRepository;
    //     this.securityUtils = securityUtils;
    // }
    // @Override
    // public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    //     OAuth2User oauth2User = super.loadUser(userRequest);
    //     String email = oauth2User.getAttribute("email");
    //     String username = oauth2User.getAttribute("name");
    //     User user = userRepository.findByEmail(email).orElseGet(() -> {
    //         User newUser = new User();
    //         newUser.setEmail(email);
    //         newUser.setUsername(username);
    //         // Ne pas définir de mot de passe pour les utilisateurs OAuth2
    //         return userRepository.save(newUser);
    //     });
    //     return new CustomOAuth2User(oauth2User, user.getId());
    // }
}
