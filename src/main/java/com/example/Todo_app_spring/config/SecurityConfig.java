package com.example.Todo_app_spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.example.Todo_app_spring.services.CustomOAuth2UserService;
import com.example.Todo_app_spring.services.UserService;

@Configuration
// @AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityUtils securityUtils;
    private final UserService userService;
    private final CustomOAuth2UserService customOAuth2UserService;
    
    @Autowired
    public SecurityConfig(SecurityUtils securityUtils, UserService userService, CustomOAuth2UserService customOAuth2UserService) {
        this.securityUtils = securityUtils;
        this.userService = userService;
        this.customOAuth2UserService = customOAuth2UserService;
    }
    
    // @Autowired
    // public void setCustomOAuth2UserService(CustomOAuth2UserService service) {
    //     this.customOAuth2UserService = service;
    // }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(securityUtils.passwordEncoder());
        return provider;
    }

    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // // Protection CSRF activée pour toutes les routes
                .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                // .csrf(csrf -> csrf
                // .ignoringRequestMatchers("/h2-console/**")
                // .disable()
                // )
                // Gestion des en-têtes HTTP
                .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
                )
                // Configuration du formulaire de connexion
                .formLogin(httpForm -> {
                    httpForm.loginPage("/req/login").permitAll();
                    httpForm.defaultSuccessUrl("/", true);
                })
            
                .oauth2Login(oauth2 -> oauth2
                .loginPage("/req/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/req/login?error=true")
                .userInfoEndpoint(userInfo -> userInfo
                .userService(customOAuth2UserService) // Méthode définie ci-dessus
                )
                .authorizationEndpoint(authorizationEndpoint ->
                authorizationEndpoint.baseUri("/oauth2/authorization")
                )
                )
                // Configuration des routes
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/req/signup", "/req/login", "/favicon.ico", "/css/**", "/js/**", "/h2-console/**").permitAll();
                    registry.anyRequest().authenticated();
                })
                // Gestion de la déconnexion
                .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                )
                .build();
    }
}
