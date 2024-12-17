package com.spring.dozen.notification.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "p_ai")
public class AI extends BaseEntity{

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ai_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    private UUID orderId;

    private UUID departureHubId;

    private UUID arrivalHubId;

    private LocalDateTime deliveryDeadline;

    private LocalDateTime recommendedDispatchTime;

    public static AI create(UUID orderId, UUID departureHubId, UUID arrivalHubId,
                            LocalDateTime deliveryDeadline, LocalDateTime recommendedDispatchTime) {
        return AI.builder()
                .orderId(orderId)
                .departureHubId(departureHubId)
                .arrivalHubId(arrivalHubId)
                .deliveryDeadline(deliveryDeadline)
                .recommendedDispatchTime(recommendedDispatchTime)
                .build();
    }
}
