package org.nastya.exception;

public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException() {
        super("Location not found");
    }
}
