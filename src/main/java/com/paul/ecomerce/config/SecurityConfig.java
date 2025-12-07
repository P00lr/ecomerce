package com.paul.ecomerce.config;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.paul.ecomerce.security.exception.RestAuthenticationEntryPoint;
import com.paul.ecomerce.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final AuthenticationProvider authenticationProvider;
        private final RestAuthenticationEntryPoint entryPoint;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .cors(Customizer.withDefaults())

                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(entryPoint))

                                .authorizeHttpRequests(auth -> auth
                                                // Rutas públicas (solo lectura)
                                                .requestMatchers(HttpMethod.GET,
                                                                "/api/v1/brands", "/api/v1/brands/**",
                                                                "/api/v1/categories", "/api/v1/categories/**",
                                                                "/api/v1/colors", "/api/v1/colors/**",
                                                                "/api/v1/sizes", "/api/v1/sizes/**",
                                                                "/api/v1/products", "/api/v1/products/**")
                                                .permitAll()

                                                // Rutas protegidas — requieren autenticación
                                                .requestMatchers(HttpMethod.POST,
                                                                "/api/v1/brands/**",
                                                                "/api/v1/categories/**",
                                                                "/api/v1/colors/**",
                                                                "/api/v1/sizes/**",
                                                                "/api/v1/products/**")
                                                .hasRole("ADMIN")

                                                .requestMatchers(HttpMethod.PUT, "/api/v1/**").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE, "/api/v1/**").hasRole("ADMIN")

                                                // Todo lo demás requiere autenticación
                                                .anyRequest().authenticated())

                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration configuration) throws Exception {

                return configuration.getAuthenticationManager();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration config = new CorsConfiguration();

                // Para desarrollo local
                config.setAllowedOrigins(List.of(
                                "http://localhost:3000",
                                "http://localhost:5173",
                                "http://localhost:4200"));

                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                config.setExposedHeaders(List.of("Authorization", "Content-Type"));
                config.setAllowCredentials(true);
                config.setMaxAge(3600L);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", config);
                return source;
        }
}
