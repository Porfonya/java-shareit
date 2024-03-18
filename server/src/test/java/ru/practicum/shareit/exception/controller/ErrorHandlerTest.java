package ru.practicum.shareit.exception.controller;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.exception.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ErrorHandlerTest {
    private final ErrorHandler errorHandler = new ErrorHandler();
    @Test
    void handleNotFoundException() {
        NotFoundException exception = new NotFoundException("Exception: NotFoundException");
        ErrorResponse result = errorHandler.handleNotFoundExceptions(exception);
        assertEquals(exception.getMessage(), result.getMessage());
    }

    @Test
    void handleInternalServiceException() {
        InternalServiceException exception = new InternalServiceException("Exception: InternalServiceException");
        ErrorResponse result = errorHandler.handleInternalServiceException(exception);
        assertEquals(exception.getMessage(), result.getMessage());
    }

    @Test
    void handleUnsupportedBookingStateException() {
        UnsupportedBookingStateException exception =
                new UnsupportedBookingStateException("Exception: UnsupportedBookingStateException");
        Map<String, String> result = Map.of("error", exception.getMessage());
       assertEquals(exception.getMessage(), result.get("error"));

    }

    @Test
    void handleValidationException() {
        ValidationException exception = new ValidationException("Exception: ValidationException");
        ErrorResponse result = errorHandler.handleValidationException(exception);
        assertEquals(exception.getMessage(), result.getMessage());
    }
}