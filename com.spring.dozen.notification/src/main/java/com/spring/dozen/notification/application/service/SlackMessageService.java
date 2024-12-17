package com.spring.dozen.notification.application.service;

import com.spring.dozen.notification.application.client.UserClient;
import com.spring.dozen.notification.application.client.dto.UserResponse;
import com.spring.dozen.notification.application.dto.slack.SlackMessageCreate;
import com.spring.dozen.notification.application.dto.slack.SlackMessageCreatedEvent;
import com.spring.dozen.notification.application.dto.slack.SlackMessageCreateResponse;
import com.spring.dozen.notification.application.dto.slack.SlackMessageResponse;
import com.spring.dozen.notification.application.exception.NotificationErrorCode;
import com.spring.dozen.notification.application.exception.NotificationException;
import com.spring.dozen.notification.domain.entity.SlackMessage;
import com.spring.dozen.notification.domain.repository.SlackMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SlackMessageService {
    private final SlackMessageRepository slackMessageRepository;
    private final UserClient userClient;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public SlackMessageCreateResponse send(SlackMessageCreate createRequest) {
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

        slackMessageRepository.save(slackMessage);

        // 이벤트 발행
        eventPublisher.publishEvent(new SlackMessageCreatedEvent(
                createRequest.messageContent(),
                userResponse.senderUserId(),
                userResponse.senderSlackId(),
                userResponse.receiverUserId(),
                userResponse.receiverSlackId()
        ));

        return SlackMessageCreateResponse.from(slackMessage, userResponse);
    }

    public SlackMessageResponse findOne(UUID slackMessageId) {
        SlackMessage slackMessage = slackMessageRepository.findById(slackMessageId)
                .orElseThrow(() -> new NotificationException(NotificationErrorCode.NOTIFICATION_NOT_FOUND));

        return SlackMessageResponse.from(slackMessage);
    }

    public Page<SlackMessageResponse> searchPage(Long senderUserId, Long receiverUserId, Pageable pageable) {
        Page<SlackMessage> slackMessagePage = slackMessageRepository.searchWithPage(senderUserId, receiverUserId, pageable);
        return slackMessagePage.map(SlackMessageResponse::from);
    }
}
