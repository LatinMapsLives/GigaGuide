package ru.rogotovskiy.userservice.exception;

public class FavoriteSightNotFoundException extends CustomException {
    public FavoriteSightNotFoundException(String error, String message) {
        super(error, message);
    }
}
