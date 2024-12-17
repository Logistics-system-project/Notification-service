package com.spring.dozen.notification.application.dto.slack;

import com.spring.dozen.notification.domain.entity.SlackMessage;

import java.time.LocalDateTime;
import java.util.UUID;

public record SlackMessageResponse(
        UUID slackMessageId,
        String messageContent,
        Long senderUserId,
        Long receiverUserId,
        LocalDateTime sentAt
) {
    public static SlackMessageResponse from(SlackMessage message) {
        return new SlackMessageResponse(
                message.getId(),
                message.getMessageContent(),
                message.getSenderUserId(),
                message.getReceiverUserId(),
                message.getSentAt()
        );
    }
}
