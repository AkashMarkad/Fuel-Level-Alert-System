package com.flas.exception;

/*
 * Kafka Exception caught in this class and send to global exception handler
 */
public class CustomKafkaException extends RuntimeException {

	public CustomKafkaException(String message) {
        super(message);
    }
}