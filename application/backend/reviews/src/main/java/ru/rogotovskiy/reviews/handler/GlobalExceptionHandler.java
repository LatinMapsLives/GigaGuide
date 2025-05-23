package ru.rogotovskiy.reviews.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.rogotovskiy.reviews.dto.ErrorResponse;
import ru.rogotovskiy.reviews.exception.ReviewException;
import ru.rogotovskiy.reviews.exception.ReviewNotFoundException;
import ru.rogotovskiy.reviews.exception.ReviewPermissionException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleReviewNotFoundException(ReviewNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(toErrorResponse(e));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleReviewPermissionException(ReviewPermissionException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(toErrorResponse(e));
    }

    private ErrorResponse toErrorResponse(ReviewException e) {
        return new ErrorResponse(
                e.getError(),
                e.getMessage(),
                LocalDateTime.now()
        );
    }
}
