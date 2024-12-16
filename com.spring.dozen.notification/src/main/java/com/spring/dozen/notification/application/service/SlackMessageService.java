package com.spring.dozen.notification.application.service;

import com.spring.dozen.notification.application.client.UserClient;
import com.spring.dozen.notification.application.client.dto.UserResponse;
import com.spring.dozen.notification.application.dto.SlackMessageCreate;
import com.spring.dozen.notification.application.dto.SlackMessageCreatedEvent;
import com.spring.dozen.notification.application.dto.SlackMessageResponse;
import com.spring.dozen.notification.application.exception.NotificationErrorCode;
import com.spring.dozen.notification.application.exception.NotificationException;
import com.spring.dozen.notification.domain.entity.SlackMessage;
import com.spring.dozen.notification.domain.repository.SlackMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SlackMessageService {
    private final SlackMessageRepository slackMessageRepository;
    private final UserClient userClient;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public SlackMessageResponse send(SlackMessageCreate createRequest) {
        log.info("send.SlackMessageCreate {}", createRequest);
        UserResponse userResponse
                = userClient.getUsersForSlack(createRequest.senderUserId(), createRequest.receiverUserId());
        log.info("send.UserResponse: {}", userResponse);
        if (userResponse.senderSlackId().isEmpty() || userResponse.receiverSlackId().isEmpty()) {
            throw new NotificationException(NotificationErrorCode.USER_NOT_FOUND);
        }
        SlackMessage slackMessage = SlackMessage.create(createRequest.messageContent(),
                userResponse.senderUserId(),
                userResponse.receiverUserId());

        // 이벤트 발행
        eventPublisher.publishEvent(new SlackMessageCreatedEvent(
                createRequest.messageContent(),
                userResponse.senderUserId(),
                userResponse.senderSlackId(),
                userResponse.receiverUserId(),
                userResponse.receiverSlackId()
        ));

        return SlackMessageResponse.from(slackMessageRepository.save(slackMessage), userResponse);
    }
}
