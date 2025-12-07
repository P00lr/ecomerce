package com.paul.ecomerce.controller;

import java.time.Duration;
import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.ecomerce.dto.auth.LoginRequestDto;
import com.paul.ecomerce.dto.auth.LoginResponseDto;
import com.paul.ecomerce.dto.user.UserRequestDto;
import com.paul.ecomerce.dto.user.UserResponseDto;
import com.paul.ecomerce.exception.custom.BusinessException;
import com.paul.ecomerce.model.entity.AppUser;
import com.paul.ecomerce.security.jwt.JwtUtil;
import com.paul.ecomerce.security.service.LoginAttemptService;
import com.paul.ecomerce.service.UserService;
import com.paul.ecomerce.service.validator.UserValidator;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

        private final UserService userService;
        private final UserValidator userValidator;
        private final AuthenticationManager authenticationManager;
        private final JwtUtil jwtUtil;
        private final LoginAttemptService loginAttemptService;

        @PostMapping("/register")
        public ResponseEntity<UserResponseDto> registerUser(
                        @Valid @RequestBody UserRequestDto userDto) {

                log.info("Registrando nuevo usuario: {}", userDto.username());
                UserResponseDto user = userService.createUser(userDto);
                log.info("Usuario registrado exitosamente: {}", userDto.username());

                return ResponseEntity.status(HttpStatus.CREATED).body(user);
        }

        @PostMapping("/login")
        public ResponseEntity<LoginResponseDto> login(
                        @Valid @RequestBody LoginRequestDto request) {

                log.info("Intento de login para usuario: {}", request.username());

                //Validar si la cuenta está bloqueada por intentos fallidos
                if (loginAttemptService.isBlocked(request.username())) {
                        log.warn("Intento de login bloqueado para: {} - Cuenta bloqueada", request.username());
                        throw new BusinessException("Cuenta bloqueada por demasiados intentos fallidos. Intenta en 15 minutos");
                }

                try {
                        // 1. Autenticar credenciales
                        authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        request.username(),
                                                        request.password()));

                        // 2. Obtener datos completos del usuario
                        AppUser user = userValidator.getUserByUsernameOrThrow(request.username());

                        // 3. Generar tokens
                        String accessToken = jwtUtil.generateToken(
                                        user.getId(),
                                        user.getUsername(),
                                        new ArrayList<>(user.getRoles()));

                        String refreshToken = jwtUtil.generateRefreshToken(
                                        user.getId(),
                                        user.getUsername());

                        //Registrar login exitoso y limpiar intentos fallidos
                        loginAttemptService.registerLoginSuccess(request.username());
                        log.info("Login exitoso para usuario: {}", request.username());

                        //Crear cookie HttpOnly para el refresh token
                        HttpHeaders headers = new HttpHeaders();
                        ResponseCookie refreshCookie = ResponseCookie
                                .from("refreshToken", refreshToken)
                                .httpOnly(true)
                                .secure(true)
                                .path("/api/auth")
                                .maxAge(Duration.ofDays(7))
                                .sameSite("Strict")
                                .build();

                        headers.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());

                        return ResponseEntity.ok()
                                .headers(headers)
                                .body(new LoginResponseDto(accessToken));

                } catch (BadCredentialsException e) {
                        //Registrar intento fallido
                        loginAttemptService.registerLoginAttempt(request.username());
                        int attempts = loginAttemptService.getAttempts(request.username());
                        
                        log.warn("Credenciales inválidas para usuario: {} - Intento: {}/5", 
                                request.username(), attempts);
                        
                        throw new BadCredentialsException("Credenciales inválidas");
                }
        }

        @PostMapping("/refresh")
        public ResponseEntity<LoginResponseDto> refresh(
                        @CookieValue(name = "refreshToken", required = false) String refreshToken) {

                if (refreshToken == null) {
                        log.warn("Intento de refresh sin token");
                        throw new BusinessException("Refresh token no encontrado");
                }

                if (!jwtUtil.isRefreshToken(refreshToken)) {
                        log.warn("Intento de refresh con token inválido");
                        throw new BusinessException("Token de refresh inválido");
                }

                String username = jwtUtil.getUsernameFromToken(refreshToken);
                Long userId = jwtUtil.getUserIdFromToken(refreshToken);

                AppUser user = userValidator.getUserOrThrow(userId);

                String newAccessToken = jwtUtil.generateToken(
                                userId,
                                username,
                                new ArrayList<>(user.getRoles()));

                log.info("Token refrescado para usuario: {}", username);

                return ResponseEntity.ok(new LoginResponseDto(newAccessToken));
        }

        @PostMapping("/logout")
        public ResponseEntity<Void> logout() {
                log.info("Logout para usuario: {}",
                                SecurityContextHolder.getContext().getAuthentication().getName());
                SecurityContextHolder.clearContext();
                return ResponseEntity.ok().build();
        }
}
