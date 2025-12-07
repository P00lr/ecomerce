package com.paul.ecomerce.security.service.impl;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

import com.paul.ecomerce.security.service.LoginAttemptService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LocalLoginAttemptService implements LoginAttemptService{

    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCK_TIME_MS = 15 * 60 * 1000; // 15 minutos
    
    private final ConcurrentHashMap<String, LoginAttempt> attempts = new ConcurrentHashMap<>();

    @Override
    public void registerLoginAttempt(String username) {
        attempts.compute(username, (key, attempt) -> {
            if (attempt == null) {
                return new LoginAttempt(1, System.currentTimeMillis());
            }
            
            // Si pasó el tiempo de bloqueo, reiniciar contador
            if (System.currentTimeMillis() - attempt.lockTime > LOCK_TIME_MS) {
                return new LoginAttempt(1, System.currentTimeMillis());
            }
            
            return new LoginAttempt(attempt.count + 1, attempt.lockTime);
        });
    }

    @Override
    public void registerLoginSuccess(String username) {
        attempts.remove(username);
        log.info("Login exitoso para: {}", username);
    }

    @Override
    public boolean isBlocked(String username) {
        LoginAttempt attempt = attempts.get(username);
        
        if (attempt == null) return false;
        
        // Si pasó el tiempo, desbloquear
        if (System.currentTimeMillis() - attempt.lockTime > LOCK_TIME_MS) {
            attempts.remove(username);
            return false;
        }
        
        return attempt.count >= MAX_ATTEMPTS;
    }

    @Override
    public int getAttempts(String username) {
        LoginAttempt attempt = attempts.get(username);
        return attempt != null ? attempt.count : 0;
    }

    private static class LoginAttempt {
        int count;
        long lockTime;

        LoginAttempt(int count, long lockTime) {
            this.count = count;
            this.lockTime = lockTime;
        }
    }
}
