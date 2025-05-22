package ru.rogotovskiy.userservice.exception;

public class EmailAlreadyExistsException extends CustomException {
    public EmailAlreadyExistsException(String error, String message) {
        super(error, message);
    }
}
