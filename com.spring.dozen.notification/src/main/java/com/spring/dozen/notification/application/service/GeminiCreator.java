package com.spring.dozen.notification.application.service;

import com.spring.dozen.notification.application.client.GeminiClient;
import com.spring.dozen.notification.application.client.dto.GeminiRequest;
import com.spring.dozen.notification.application.client.dto.GeminiResponse;
import com.spring.dozen.notification.application.dto.ai.AICreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Component
public class GeminiCreator {
    private final GeminiClient geminiClient;

    public LocalDateTime create(AICreate createRequest) {
        log.info("create.AICreate {}", createRequest);

        GeminiRequest geminiRequest = from(createRequest);
        GeminiResponse response = geminiClient.getCompletion(geminiRequest);
        log.info("GeminiResponse: {}", response);

        return LocalDateTime.parse(response.getDeadLine(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private GeminiRequest from(AICreate createRequest) {
        String query = """
                다음 주문 정보를 분석하고 최종 발송 시한을 계산해주세요. 
                deadline이 도착기한입니다. 
                배달트럭으로 운송합니다. 
                최종 발송 시한만 응답해주세요. 
                결과는 ISO 8601 형식의 날짜와 시간(예: 2024-12-16T21:00:00)으로 반환해주세요.
                """;
        String start = createRequest.departureAddress();
        String destination = createRequest.arrivalAddress();
        String deadline = createRequest.deliveryDeadline().toString();

        return new GeminiRequest(query, start, destination, deadline);
    }
}
