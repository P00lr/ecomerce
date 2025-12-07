package com.paul.ecomerce.exception.custom;

public class JwtValidationException extends RuntimeException {
    public JwtValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtValidationException(String mesaage) {
        super(mesaage);
    }
}
