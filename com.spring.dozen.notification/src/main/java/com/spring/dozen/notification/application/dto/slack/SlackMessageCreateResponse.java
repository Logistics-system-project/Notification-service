package com.spring.dozen.notification.application.dto.slack;

import com.spring.dozen.notification.application.client.dto.UserResponse;
import com.spring.dozen.notification.domain.entity.SlackMessage;

import java.time.LocalDateTime;
import java.util.UUID;

public record SlackMessageCreateResponse(
        UUID slackMessageId,
        String messageContent,
        Long senderUserId,
        String senderSlackId,
        Long receiverUserId,
        String receiverSlackId,
        LocalDateTime sentAt
) {
    public static SlackMessageCreateResponse from(SlackMessage slackMessage, UserResponse userResponse) {
        return new SlackMessageCreateResponse(
                slackMessage.getId(),
                slackMessage.getMessageContent(),
                slackMessage.getSenderUserId(),
                userResponse.senderSlackId(),
                slackMessage.getReceiverUserId(),
                userResponse.receiverSlackId(),
                slackMessage.getSentAt()
        );
    }
}
