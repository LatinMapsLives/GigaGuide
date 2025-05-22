package ru.rogotovskiy.userservice.exception;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(String error, String message) {
        super(error, message);
    }
}
