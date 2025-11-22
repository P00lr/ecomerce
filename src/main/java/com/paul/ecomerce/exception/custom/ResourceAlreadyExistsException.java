package com.paul.ecomerce.exception.custom;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String message ) {
        super(message);
    }
}
