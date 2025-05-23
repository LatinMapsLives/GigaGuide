package ru.rogotovskiy.reviews.exception;

import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException {

    private final String error;

    public ReviewException(String error, String message) {
        super(message);
        this.error = error;
    }
}
