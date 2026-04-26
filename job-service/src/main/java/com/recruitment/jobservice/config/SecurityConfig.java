package com.recruitment.jobservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.http.HttpMethod;


@Configuration
public class SecurityConfig {

    private static final String SECRET = "my-secret-key-my-secret-key-my-secret";

    private SecretKey key() {
        return new SecretKeySpec(SECRET.getBytes(), "HmacSHA256");
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // public
                        .requestMatchers("/api/jobs/search/**").permitAll()

                        // GET = public
                        .requestMatchers(HttpMethod.GET, "/api/jobs").permitAll()

                        // POST = seulement recruteur
                        .requestMatchers(HttpMethod.POST, "/api/jobs")
                        .hasRole("RECRUTEUR")

                        // autres endpoints protégés
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(new JwtAuthConverter())
                        )
                );

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey key = new SecretKeySpec(
                "my-secret-key-my-secret-key-my-secret".getBytes(),
                "HmacSHA256"
        );

        return NimbusJwtDecoder.withSecretKey(key).build();
    }
}