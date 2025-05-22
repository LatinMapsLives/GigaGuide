package ru.rogotovskiy.toursight.exception;

public class TourNotFoundException extends ObjectNotFoundException {
    public TourNotFoundException(String message) {
        super(message);
    }
}
