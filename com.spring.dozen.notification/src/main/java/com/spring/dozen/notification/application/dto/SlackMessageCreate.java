package com.spring.dozen.notification.application.dto;

public record SlackMessageCreate(
        String messageContent,
        Long senderUserId,
        Long receiverUserId
) {
}
