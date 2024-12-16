package com.spring.dozen.notification.application.service;

import com.spring.dozen.notification.application.dto.ai.AiResponse;
import com.spring.dozen.notification.application.dto.ai.AICreate;
import com.spring.dozen.notification.domain.entity.AI;
import com.spring.dozen.notification.domain.repository.AIRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AIService {
    private final AIRepository aiRepository;
    private final GeminiCreator geminiCreator;

    public AiResponse create(AICreate createRequest) {
        log.info("create.AICreate {}", createRequest);
        LocalDateTime responseDeadline = geminiCreator.create(createRequest);
        log.info("create.responseDeadline {}", responseDeadline);

        AI ai = AI.create(createRequest.orderId(),
                createRequest.departureHubId(),
                createRequest.arrivalHubId(),
                createRequest.deliveryDeadline(),
                responseDeadline);
        aiRepository.save(ai);

        return AiResponse.from(ai, createRequest);
    }

}
