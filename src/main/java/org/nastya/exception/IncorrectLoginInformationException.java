package org.nastya.exception;

public class IncorrectLoginInformationException extends RuntimeException {
    public IncorrectLoginInformationException() {
        super("The username or password is incorrect");
    }
}
