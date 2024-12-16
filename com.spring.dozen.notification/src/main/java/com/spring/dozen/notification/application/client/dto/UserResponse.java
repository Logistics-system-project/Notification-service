package com.spring.dozen.notification.application.client.dto;

public record UserResponse(
        Long senderUserId,
        String senderSlackId,
        Long receiverUserId,
        String receiverSlackId
) {
}
