package ru.rogotovskiy.userservice.exception;

public class CustomException extends RuntimeException {
    private final String error;

    public CustomException(String error, String message) {
        super(message);
        this.error = error;
    }

    public String getError() {
        return this.error;
    }
}
