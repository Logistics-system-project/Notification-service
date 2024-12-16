package com.spring.dozen.notification.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotificationException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;

    public NotificationException(NotificationErrorCode authErrorCode) {
        this.httpStatus = authErrorCode.getHttpStatus();
        this.message = authErrorCode.getMessage();
    }
}
