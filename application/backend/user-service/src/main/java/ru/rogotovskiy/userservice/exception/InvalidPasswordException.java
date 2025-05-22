package ru.rogotovskiy.userservice.exception;

public class InvalidPasswordException extends CustomException {
    public InvalidPasswordException(String error, String message) {
        super(error, message);
    }
}
