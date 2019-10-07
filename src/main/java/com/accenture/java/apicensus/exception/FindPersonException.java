package com.accenture.java.apicensus.exception;

/**
 * A custom exception used when an unhandled
 * exception occurs during the find person process.
 *
 * @author Gian F. S.
 */
public class FindPersonException extends Exception {

    public FindPersonException(String message) {
        super(message);
    }

    public FindPersonException(String message, Throwable cause) {
        super(message, cause);
    }

    public FindPersonException(Throwable cause) {
        super(cause);
    }

    public FindPersonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
