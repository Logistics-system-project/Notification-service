package com.spring.dozen.notification.application.dto.ai;

import com.spring.dozen.notification.domain.entity.AI;

import java.time.LocalDateTime;

public record AiResponse(
        String aiId,
        String orderId,
        String departureAddress,
        String arrivalAddress,
        LocalDateTime deliveryDeadline,
        LocalDateTime recommendedDispatchTime
) {

    public static AiResponse from(AI ai, AICreate aiCreate) {
        return new AiResponse(
                ai.getId().toString(),
                ai.getOrderId().toString(),
                aiCreate.departureAddress(),
                aiCreate.arrivalAddress(),
                ai.getDeliveryDeadline(),
                ai.getRecommendedDispatchTime()
        );
    }
}
