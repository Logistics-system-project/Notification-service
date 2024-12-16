package com.spring.dozen.notification.application.dto.slack;

public record SlackMessageCreate(
        String messageContent,
        Long senderUserId,
        Long receiverUserId
) {
}
