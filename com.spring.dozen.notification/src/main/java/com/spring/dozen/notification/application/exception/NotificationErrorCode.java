package com.spring.dozen.notification.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NotificationErrorCode {

    // UNAUTHORIZED & FORBIDDEN
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 존재하지 않습니다."),
    MISSING_ROLE(HttpStatus.BAD_REQUEST, "권한 정보가 없습니다."),

    // Notification,
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 메시지 정보가 존재하지 않습니다."),
    SLACK_MESSAGE_SEND_FAILED(HttpStatus.NOT_FOUND,"슬랙 메시지 전송에 실패했습니다."),

    // User;
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "발신자 또는 수신자 정보가 존재하지 않습니다.")

    ;

    private final HttpStatus httpStatus;
    private final String message;
}
