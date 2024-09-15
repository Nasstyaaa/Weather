package org.nastya.exception;

public class IncorrectLoginInformation extends RuntimeException {
    public IncorrectLoginInformation() {
        super("The username or password is incorrect");
    }
}
