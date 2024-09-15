package org.nastya.exception;

public class InternalServerException extends RuntimeException{
    public InternalServerException() {
        super("Internal server error");
    }
}
