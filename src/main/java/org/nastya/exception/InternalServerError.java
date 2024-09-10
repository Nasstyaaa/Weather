package org.nastya.exception;

public class InternalServerError extends RuntimeException{
    public InternalServerError() {
        super("Internal server error");
    }
}
