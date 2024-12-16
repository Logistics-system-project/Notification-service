package com.spring.dozen.notification.presentation.dto;

import com.spring.dozen.notification.application.dto.SlackMessageCreate;

public record SlackMessageCreateRequest(
        String messageContent,
        Long senderUserId,
        Long receiverUserId
) {

    public SlackMessageCreate toServiceDto() {
        return new SlackMessageCreate(messageContent, senderUserId, receiverUserId);
    }
}
