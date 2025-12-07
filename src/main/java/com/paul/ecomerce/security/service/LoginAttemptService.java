package com.paul.ecomerce.security.service;

public interface LoginAttemptService {

    void registerLoginAttempt(String username);

    void registerLoginSuccess(String username);

    boolean isBlocked(String username);

    int getAttempts(String username);
}
