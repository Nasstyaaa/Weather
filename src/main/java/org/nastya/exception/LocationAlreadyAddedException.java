package org.nastya.exception;

public class LocationAlreadyAddedException extends RuntimeException {
    public LocationAlreadyAddedException() {
        super("The location has already been added");
    }
}
