package com.spring.dozen.notification.infra.event;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.spring.dozen.notification.application.dto.SlackMessageCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Async
@Component
public class SlackMessageSendListener {
    private final MethodsClient slackClient;

    @EventListener
    public void handleSlackMessageSend(SlackMessageCreatedEvent event) {
        try {
            log.info("handleSlackMessageSend.SlackMessageCreatedEvent: {}", event);
            ChatPostMessageResponse response = slackClient.chatPostMessage(req -> req
                    .channel(event.receiverSlackId())
                    .text(event.messageContent())
                    .asUser(true));

            if (!response.isOk()) {
                log.error("Failed to send Slack message: {}", response.getError());
                // 재시도 로직 구현 가능
            }
        } catch (Exception e) {
            log.error("Error sending Slack message", e);
            // 재시도 로직 구현 가능
        }
    }
}
