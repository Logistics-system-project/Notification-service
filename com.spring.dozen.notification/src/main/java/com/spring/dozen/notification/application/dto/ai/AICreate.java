package com.spring.dozen.notification.application.dto.ai;

import java.time.LocalDateTime;
import java.util.UUID;

public record AICreate(
        UUID orderId,
        UUID departureHubId,
        String departureAddress,
        UUID arrivalHubId,
        String arrivalAddress,
        LocalDateTime deliveryDeadline,
        Long senderUserId,
        Long receiverUserId
) {
}
