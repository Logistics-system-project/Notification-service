package com.spring.dozen.notification.application.dto.ai;

import com.spring.dozen.notification.domain.entity.AI;

import java.time.LocalDateTime;

public record AiListResponse(
        String aiId,
        String orderId,
        String departureHubId,
        String arrivalHubId,
        LocalDateTime deliveryDeadline,
        LocalDateTime recommendedDispatchTime
) {

    public static AiListResponse from(AI ai) {
        return new AiListResponse(
                ai.getId().toString(),
                ai.getOrderId().toString(),
                ai.getDepartureHubId().toString(),
                ai.getArrivalHubId().toString(),
                ai.getDeliveryDeadline(),
                ai.getRecommendedDispatchTime()
        );
    }
}

