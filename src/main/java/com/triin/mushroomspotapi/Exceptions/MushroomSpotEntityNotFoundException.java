package com.triin.mushroomspotapi.Exceptions;

public class MushroomSpotEntityNotFoundException extends RuntimeException {

    public MushroomSpotEntityNotFoundException(String message) {
        super(message);
    }

    public MushroomSpotEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MushroomSpotEntityNotFoundException() {
        super("MushroomSpot entity not found.");
    }
}
