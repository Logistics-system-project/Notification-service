package com.spring.dozen.notification.application.service;

import com.spring.dozen.notification.application.client.UserClient;
import com.spring.dozen.notification.application.client.dto.UserResponse;
import com.spring.dozen.notification.application.dto.ai.AiResponse;
import com.spring.dozen.notification.application.dto.ai.AICreate;
import com.spring.dozen.notification.application.dto.slack.SlackMessageCreatedEvent;
import com.spring.dozen.notification.domain.entity.AI;
import com.spring.dozen.notification.domain.repository.AIRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AIService {
    private final AIRepository aiRepository;
    private final GeminiCreator geminiCreator;
    private final UserClient userClient;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public AiResponse create(AICreate createRequest) {
        log.info("create.AICreate {}", createRequest);
        // Gemini 호출
        LocalDateTime responseDeadline = geminiCreator.create(createRequest);
        log.info("create.responseDeadline {}", responseDeadline);

        // 발신자, 수신자 정보 조회
        UserResponse userResponse = userClient.getUsersForSlack(createRequest.senderUserId(), createRequest.receiverUserId());
        log.info("create.userResponse {}", userResponse);

        AI ai = AI.create(createRequest.orderId(),
                createRequest.departureHubId(),
                createRequest.arrivalHubId(),
                createRequest.deliveryDeadline(),
                responseDeadline);
        aiRepository.save(ai);

        // 이벤트 발행
        eventPublisher.publishEvent(new SlackMessageCreatedEvent(
                String.format("출발 주소: %s\n도착 주소: %s\n납기일자: %s\n최종 발송 시한: %s",
                        createRequest.departureAddress(),
                        createRequest.arrivalAddress(),
                        createRequest.deliveryDeadline(),
                        responseDeadline),
                userResponse.senderUserId(),
                userResponse.senderSlackId(),
                userResponse.receiverUserId(),
                userResponse.receiverSlackId()
        ));

        return AiResponse.from(ai, createRequest);
    }

}
