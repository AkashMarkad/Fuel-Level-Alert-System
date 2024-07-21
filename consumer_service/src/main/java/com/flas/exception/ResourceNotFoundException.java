package com.flas.exception;

/*
 * Here handling the resource not found exception
 */
public class ResourceNotFoundException extends RuntimeException {
	
    public ResourceNotFoundException(String message) {
        super(message);
    }
}