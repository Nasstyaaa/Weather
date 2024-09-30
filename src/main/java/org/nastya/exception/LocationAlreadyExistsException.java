package org.nastya.exception;

public class LocationAlreadyExistsException extends RuntimeException {
    public LocationAlreadyExistsException() {
        super("The location has already been added");
    }
}
