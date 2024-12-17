package com.spring.dozen.notification.presentation.dto.slack;

import com.spring.dozen.notification.application.dto.slack.SlackMessageCreate;

public record SlackMessageCreateRequest(
        String messageContent,
        Long senderUserId,
        Long receiverUserId
) {

    public SlackMessageCreate toServiceDto() {
        return new SlackMessageCreate(messageContent, senderUserId, receiverUserId);
    }
}
