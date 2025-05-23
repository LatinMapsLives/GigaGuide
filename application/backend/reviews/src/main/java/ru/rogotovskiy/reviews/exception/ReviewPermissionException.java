package ru.rogotovskiy.reviews.exception;

public class ReviewPermissionException extends ReviewException {

    public ReviewPermissionException(String error, String message) {
        super(error, message);
    }
}
