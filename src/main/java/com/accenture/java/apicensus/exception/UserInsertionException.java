package com.accenture.java.apicensus.exception;

/**
 * Exception used when you try to insert
 * an existing user into database.
 *
 * @author Gian F. S.
 */
public class UserInsertionException extends RuntimeException {

    public UserInsertionException() {
        super();
    }
}
