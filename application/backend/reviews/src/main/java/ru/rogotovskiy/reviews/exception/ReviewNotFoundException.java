package ru.rogotovskiy.reviews.exception;

public class ReviewNotFoundException extends ReviewException {

    public ReviewNotFoundException(String error, String message) {
        super(error, message);
    }
}
