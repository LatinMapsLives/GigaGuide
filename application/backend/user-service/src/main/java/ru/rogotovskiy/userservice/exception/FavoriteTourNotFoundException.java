package ru.rogotovskiy.userservice.exception;

public class FavoriteTourNotFoundException extends CustomException {
    public FavoriteTourNotFoundException(String error, String message) {
        super(error, message);
    }
}
