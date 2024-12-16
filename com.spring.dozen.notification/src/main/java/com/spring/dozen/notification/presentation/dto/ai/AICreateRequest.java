package com.spring.dozen.notification.presentation.dto.ai;

import com.spring.dozen.notification.application.dto.ai.AICreate;

import java.time.LocalDateTime;
import java.util.UUID;

public record AICreateRequest(
        UUID orderId,
        UUID departureHubId,
        String departureAddress,
        UUID arrivalHubId,
        String arrivalAddress,
        LocalDateTime deliveryDeadline
) {
    public AICreate toAICreate() {
        return new AICreate(orderId, departureHubId, departureAddress, arrivalHubId, arrivalAddress, deliveryDeadline);
    }
}
