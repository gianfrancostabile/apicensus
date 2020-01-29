package com.accenture.java.apicensus.exception;

/**
 * Exception used when an error occurs
 * during the database person insertion
 * process.
 *
 * @author Gian F. S.
 */
public class PersonInsertionException extends RuntimeException {

    public PersonInsertionException(String message) {
        super(message);
    }
}
