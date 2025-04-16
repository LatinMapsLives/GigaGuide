package ru.rogotovskiy.toursight.exception;

public class SightNotFoundException extends ObjectNotFoundException {
    public SightNotFoundException(String message) {
        super(message);
    }
}
