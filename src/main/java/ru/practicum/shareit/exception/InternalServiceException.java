package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InternalServiceException extends RuntimeException {
    public InternalServiceException(final String message) {
        log.error(message);
    }
}
