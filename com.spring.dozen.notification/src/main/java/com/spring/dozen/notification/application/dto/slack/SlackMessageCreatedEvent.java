package com.spring.dozen.notification.application.dto.slack;

public record SlackMessageCreatedEvent(
        String messageContent,
        Long senderUserId,
        String senderSlackId,
        Long receiverUserId,
        String receiverSlackId
) {
}