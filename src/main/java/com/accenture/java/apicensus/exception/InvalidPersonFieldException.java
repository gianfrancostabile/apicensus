package com.accenture.java.apicensus.exception;

/**
 * Exception used when person validation
 * throws BeanValidationException.
 *
 * @author Gian F. S.
 */
public class InvalidPersonFieldException extends RuntimeException {

    public InvalidPersonFieldException(String message) {
        super(message);
    }
}
